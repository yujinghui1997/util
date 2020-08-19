package com.yjh.properties;

import cn.hutool.core.util.StrUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "com.yjh.cros")
public class MyCrosFilterProperties implements Condition {

    private Boolean open = false;
    private Integer order = 0;
    private String origin = "*";
    private String[] headers = {"*"};

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String value = conditionContext.getEnvironment().getProperty("com.yjh.cros.open");
        if (StrUtil.isNotBlank(value)){
            if (value.equals("true")){
                return true;
            }
        }
        return false;
    }
}
