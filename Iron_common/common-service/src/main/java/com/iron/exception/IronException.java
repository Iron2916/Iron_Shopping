package com.iron.exception;


import com.iron.model.Result.ResultCodeEnum;
import lombok.Data;

@Data
public class IronException extends RuntimeException {

    private Integer code ;          // 错误状态码
    private String message ;        // 错误消息

    private ResultCodeEnum resultCodeEnum ;     // 封装错误状态码和错误消息

    public IronException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum ;
        this.code = resultCodeEnum.getCode() ;
        this.message = resultCodeEnum.getMessage();
    }

    public IronException(Integer code , String message) {
        this.code = code ;
        this.message = message ;
    }

}