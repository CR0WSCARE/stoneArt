package com.example.backend.config;

import com.example.backend.common.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class interceptionCofig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(JwtInterceptor()) // 添加JWT拦截器
                .addPathPatterns("/**")// 拦截所有请求
                .excludePathPatterns("/admin/**");//对admin路径下的请求不进行拦截
        super.addInterceptors(registry);
    }

    @Bean
    public JwtInterceptor JwtInterceptor() {
        return new JwtInterceptor(); // 创建JwtInterceptor的Bean
    }
}
