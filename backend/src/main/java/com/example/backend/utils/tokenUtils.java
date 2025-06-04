package com.example.backend.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.backend.mapper.userMapper;
import com.example.backend.pojo.user;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

@Component
public class tokenUtils {

    private static userMapper staticUserMapper;

    @Resource
    userMapper userMapper;

    @PostConstruct
    public void setStaticUserMapper() {
        staticUserMapper = userMapper; // 将userMapper注入到静态变量中
    }

    public static String genToken(String userId,String sign) {
        return JWT.create().withAudience(userId)
                .withExpiresAt(DateUtil.offsetHour(new Date(),1)) // 设置token过期时间为1小时
                .sign(Algorithm.HMAC256(sign));
    }

    public static user getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token =  request.getHeader("token");
            if (StrUtil.isBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserMapper.selectById(Integer.valueOf(userId)); // 如果没有token，返回null
            }
        } catch (Exception e) {
            return null; // 如果获取用户信息失败，返回null
        }
        return null; // 如果没有token，返回null
    }

}
