package com.yjh.log;

import com.yjh.util.MyLdtUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Configuration
public class AopComment {

    private static final String AOP_LOG = "@annotation(com.yjh.annotation.MyAopLog)";
    private static final Logger log = LoggerFactory.getLogger(AopComment.class);

    @Async
    @Before(AOP_LOG)
    public void aopLog(JoinPoint joinPoint)  {
        StringBuilder sb = new StringBuilder();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        sb.append("请求地址：").append(className).append(".").append(methodName).append("  ");
        sb.append("请求时间：").append(MyLdtUtil.getStr()).append("  ").append("请求参数：");
        Map<String, Object> params = getNameAndValue(joinPoint);
        for (Map.Entry<String, Object> param : params.entrySet()) {
            sb.append(param.getKey()).append("：").append(param.getValue()).append("  ");
        }
        log.info(sb.toString());
    }

    private Map<String, Object> getNameAndValue(JoinPoint joinPoint) {
        Map<String, Object> params = new HashMap<String, Object>();
        // 参数数组
        Object[] values = joinPoint.getArgs();
        // 参数名称
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        String[] keys = signature.getParameterNames();
        for (int i = 0; i < keys.length; i++) {
            params.put(keys[i], values[i]);
        }
        return params;
    }
}
