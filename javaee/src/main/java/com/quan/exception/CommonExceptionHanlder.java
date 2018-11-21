package com.quan.exception;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Executors;

@ControllerAdvice
public class CommonExceptionHanlder {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResponse response(Exception e){
        if(e instanceof UnauthenticatedException){
            return new CommonResponse("501","用户未登录");
        }
        if(e instanceof UnauthorizedException){
            return new CommonResponse("502","暂无访问权限");
        }
        else{
            return new CommonResponse("500","系统错误");
        }
    }
}
