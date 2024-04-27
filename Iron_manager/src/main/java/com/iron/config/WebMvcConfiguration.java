package com.iron.config;

import com.iron.interceptor.LoginAuthInterceptor;
import com.iron.properties.UserAuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private UserAuthProperties userAuthProperties ;		// 注入实体类对象

    @Autowired
    private LoginAuthInterceptor loginAuthInterceptor ;

    /**
     * 配置拦截器：实现登录拦截功能
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthInterceptor)
                .excludePathPatterns(userAuthProperties.getNoAuthUrls())
                .excludePathPatterns("/favicon.ico","/swagger-resources/**", "/webjars/**", "/v3/**", "/swagger-ui.html/**", "/doc.html", "/device-width")
                .addPathPatterns("/**");
    }

    /**
     * 跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")      // 添加路径规则
                .allowCredentials(true)               // 是否允许在跨域的情况下传递Cookie
                .allowedOriginPatterns("*")           // 允许请求来源的域规则
            	.allowedMethods("*")
                .allowedHeaders("*") ;                // 允许所有的请求头
    }
    
}