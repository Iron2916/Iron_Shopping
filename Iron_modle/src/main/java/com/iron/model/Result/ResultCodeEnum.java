package com.iron.model.Result;

import lombok.Getter;

@Getter // 提供获取属性值的getter方法
public enum ResultCodeEnum {

    SUCCESS(200 , "操作成功") ,
    LOGIN_ERROR(201 , "用户名或者密码错误"),

    VALIDATECODE_ERROR(202, "输入验证码错误或验证码已过期"),

    LOGIN_AUTH(208, "未登录请登陆后进行操作");
    private Integer code ;      // 业务状态码
    private String message ;    // 响应消息

    private ResultCodeEnum(Integer code , String message) {
        this.code = code ;
        this.message = message ;
    }
}