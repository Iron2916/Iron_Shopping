package com.iron.interceptor;

import com.alibaba.fastjson2.JSON;
import com.iron.ThreadContextUtil;
import com.iron.RedisKeyHeader;
import com.iron.model.Result.Result;
import com.iron.model.Result.ResultCodeEnum;
import com.iron.model.entity.system.SysUser;
import com.iron.properties.UserAuthProperties;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class LoginAuthInterceptor implements HandlerInterceptor {

    @Resource
    RedisTemplate redisTemplate;

    /**
     * 放行OPTIONS操作， 查询是否带有token，通过查询是否带有token实现登录拦截和过期验证，将用户存入线程
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 放过为 OPTIONS 的预请求
        final String method = request.getMethod();
        if ("OPTIONS".equals(method)) {

            return true;
        }

        // 根据token判断用户是登录或者是否过期
        final String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {

            response.setContentType("application/json;charset=utf-8");
            response.getWriter().println(JSON.toJSON(Result.build(null, ResultCodeEnum.LOGIN_AUTH)));
            return false;
        }
        final Object sysObject = redisTemplate.opsForValue().get(RedisKeyHeader.userLogin + token);

        if (sysObject == null) {

            response.setContentType("application/json;charset=utf-8");
            response.getWriter().println(JSON.toJSON(Result.build(null, ResultCodeEnum.LOGIN_AUTH)));
            return false;
        }

        final SysUser sysUser = JSON.parseObject(sysObject.toString(), SysUser.class);
        ThreadContextUtil.set(sysUser);
        return true;
    }

    /**
     * 释放线程资源
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        ThreadContextUtil.remove();
    }


}
