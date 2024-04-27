package com.iron.controller;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.iron.RedisKeyHeader;
import com.iron.ThreadContextUtil;
import com.iron.exception.IronException;
import com.iron.model.Result.Result;
import com.iron.model.Result.ResultCodeEnum;
import com.iron.model.dto.system.LoginDto;
import com.iron.model.dto.system.SysUserDto;
import com.iron.model.entity.system.SysUser;
import com.iron.model.vo.system.LoginVo;
import com.iron.model.vo.system.ValidateCodeVo;
import com.iron.service.SysUserService;
import com.iron.service.ValidateService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * 登录controller
 */
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    ValidateService validateService;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 登录
     * @param userDto
     * @return loginVo
     */
    @Operation(summary = "登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto userDto) {


        /*final String captcha = userDto.getCaptcha();    // 用户输入验证码
        final String codeKey = userDto.getCodeKey();    // Redis中存储的验证码
        // 从Redis中获取验证码验证验证正确和是否过期
        String redisCode = redisTemplate.opsForValue().get(RedisKeyHeader.validateCode + codeKey).toString();
        if(StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode , captcha)) {
            throw new IronException(ResultCodeEnum.VALIDATECODE_ERROR) ;
        }
        // 删除对应的code
        redisTemplate.delete(RedisKeyHeader.validateCode+codeKey);*/

        final LoginVo loginVo = sysUserService.selectByUserName(userDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 生成验证码并将器存入redis中
     * @return
     */
    @Operation(summary = "生成验证码")
    @GetMapping("/generateValidateCode")
    public Result generatevalidateCode() {

        ValidateCodeVo validateCodeVo = validateService.generateValidateCode();
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 获取用户登录信息，从ThreadLocal(因为会走拦截器并且拦截器里面进行了将用户信息存入了线程
     * @param
     * @return
     */
    @Operation(summary = "获取登录用户信息")
    @GetMapping("/userinfo")
    public Result getUserInfo() {

        SysUser sysUser = ThreadContextUtil.get();

        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
    }

    /**
     * 退出登录，清除存在redis中的用户
     * @param token
     * @return
     */
    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader(value = "token") String token) {

        sysUserService.logout(token) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
}
