package com.iron.exception;

import com.iron.model.Result.Result;
import com.iron.model.Result.ResultCodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public Result globalException(Exception e) {

        e.printStackTrace();
        return Result.build(null, 201,"全局异常,请稍后再试一试");
    }

    @ExceptionHandler(IronException.class)
    public Result ironException(IronException e) {

        return Result.build(null, e.getResultCodeEnum());
    }
}
