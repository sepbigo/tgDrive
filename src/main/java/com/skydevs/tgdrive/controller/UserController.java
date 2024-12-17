package com.skydevs.tgdrive.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.skydevs.tgdrive.dto.AuthRequest;
import com.skydevs.tgdrive.dto.UserLogin;
import com.skydevs.tgdrive.entity.User;
import com.skydevs.tgdrive.result.Result;
import com.skydevs.tgdrive.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<UserLogin> login(@RequestBody AuthRequest authRequest) {
        // 验证用户名和密码
        User user = userService.login(authRequest);

        StpUtil.login(user.getId());
        UserLogin userLogin = UserLogin.builder()
                .UserId(user.getId())
                .token(StpUtil.getTokenValue())
                .role(user.getRole())
                .build();

        return Result.success(userLogin);
    }
}
