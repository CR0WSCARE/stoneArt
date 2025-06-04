package com.example.backend.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.backend.exception.serviceException;
import com.example.backend.mapper.userMapper;
import com.example.backend.pojo.user;
import com.fasterxml.jackson.core.JacksonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;

public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private userMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String token = request.getHeader("token"); // 从请求头中获取token
        if(StrUtil.isBlank(token)){
            token = request.getParameter("token"); // 从请求参数中获取token
        }
        if(handler instanceof HandlerMethod){
            AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            if(annotation != null){
                return true;
            }
        }

        if(StrUtil.isBlank(token)){
            throw new serviceException("401","请重新登录");
        }

        String userId;
        try{
            userId = JWT.decode(token).getAudience().get(0); // 解码JWT获取用户ID
        } catch (JWTDecodeException j){
            throw new serviceException("401","请重新登录");
        }

        user user = userMapper.selectById(Integer.valueOf(userId)); // 根据用户ID查询用户信息
        if(user == null){
            throw new serviceException("401","请重新登录");
        }

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build(); // 创建JWT验证器
        try {
            verifier.verify(token); // 验证token
        } catch (Exception e) {
            throw new serviceException("401","请重新登录");
        }
        return true;
    }
}
