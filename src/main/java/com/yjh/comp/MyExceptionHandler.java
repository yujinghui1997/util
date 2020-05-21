package com.yjh.comp;

import com.yjh.core.MyHttpStatus;
import com.yjh.core.ResData;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class MyExceptionHandler {



    @ExceptionHandler(BindException.class)
    public ResData bindException(BindException ex) {
        String message = ex.getBindingResult().getAllErrors().stream()
                        .map(s -> s.getDefaultMessage())
                        .collect(Collectors.joining(","));
        return ResData.fail(MyHttpStatus.PARAM_ERR, message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResData bindException(MethodArgumentNotValidException ex) {
        String message =
                ex.getBindingResult().getAllErrors().stream()
                        .map(s -> s.getDefaultMessage())
                        .collect(Collectors.joining(","));
        return ResData.fail(MyHttpStatus.PARAM_ERR, message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResData bindException(ConstraintViolationException ex) {
        String message =
                ex.getConstraintViolations().stream()
                        .map(s -> s.getMessage())
                        .collect(Collectors.joining(","));
        return ResData.fail(MyHttpStatus.PARAM_ERR, message);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResData missingServletRequestParameterException(
            MissingServletRequestParameterException ex) {
        return ResData.fail(MyHttpStatus.REQUEST_ERR, ex.getParameterName() + " 为必传参数");
    }

    /**
     * jwt异常
     * @param ex
     * @return
     */
    @ExceptionHandler(JwtException.class)
    public ResData jwtException(JwtException ex) {
        if (ex instanceof ExpiredJwtException) {
            String txt = "令牌验证失效，请重新登陆";
            return ResData.fail(MyHttpStatus.TOKEN_ERR, txt);
        }
        if (ex instanceof MalformedJwtException) {
            String txt = "token格式错误";
            return ResData.fail(MyHttpStatus.TOKEN_ERR, txt);
        }
        if (ex instanceof SignatureException) {
            String txt = "非法token";
            return ResData.fail(MyHttpStatus.TOKEN_ERR, txt);
        }
        return ResData.fail(MyHttpStatus.TOKEN_ERR, "jwt错误");
    }


}
