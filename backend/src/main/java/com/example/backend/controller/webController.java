package com.example.backend.controller;

import cn.hutool.core.util.StrUtil;
import com.example.backend.common.AuthAccess;
import com.example.backend.common.Result;
import com.example.backend.pojo.user;
import com.example.backend.service.userService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin(value = "http://localhost:9000")
@RestController
@RequestMapping("/admin")
public class webController {
    @Resource
    userService userService;

    @GetMapping("/")
    public Result hello() {
        // 返回欢迎信息
        return Result.success("Hello World");
    }

    @PostMapping("/login") // 管理员登录接口
    public Result login(@RequestBody user user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())) {
            return Result.error("-1", "用户名或密码不能为空");
        }
        user = userService.login(user);
        return Result.success(user);
    }
}
