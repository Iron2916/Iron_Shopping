package com.iron.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.lang.UUID;
import com.iron.RedisKeyHeader;
import com.iron.model.vo.system.ValidateCodeVo;
import com.iron.service.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ValidateServiceImpl implements ValidateService {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 使用 hutool 工具包生成 验证码图片
     * @return
     */
    @Override
    public ValidateCodeVo generateValidateCode() {

        // 利用糊涂工具包生成验证码图片：高 宽 验证码位数 干扰数量
        final CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 2);
        final String code = circleCaptcha.getCode();
        final String imageBase64 = circleCaptcha.getImageBase64();

        // 将生成的验证码code加入到redis中保存：便于以后判断验证码是否过期
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(RedisKeyHeader.validateCode+uuid, code, 5, TimeUnit.MINUTES);

        // 封装响应对象
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(uuid);
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);

        return validateCodeVo;
    }
}
