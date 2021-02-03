package com.wzliulan.demo.spca.user.controller;

import com.wzliulan.demo.spca.user.auth.CheckLogin;
import com.wzliulan.demo.spca.user.domain.dto.LoginRequestDto;
import com.wzliulan.demo.spca.user.domain.dto.LoginResponseDto;
import com.wzliulan.demo.spca.user.domain.model.User;
import com.wzliulan.demo.spca.user.service.UserService;
import com.wzliulan.demo.spca.user.utils.JwtOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtOperator jwtOperator;

    //@CheckLogin
    @GetMapping("/{id}")
    public Object findById(@PathVariable Integer id) {
        log.info("*** 方法已被请求执行...");
        User user = userService.findUser(id);
        return user;
    }


    // 配合Feign构造Get型的多参数请求
    // 测试地址：http://localhost:8100/user/query?id=201&wxNickname=lihailin9073
    @GetMapping("/query")
    public Object query(User user) {
        log.info("*** query方法已被请求执行...");
        // TODO
        user.setWxId(UUID.randomUUID().toString()); // 设置微信ID为随机字符
        return user;
    }
    // 配合Feign构造Post型的多参数请求
    // 测试地址[Postman]：http://localhost:8100/user/search
    @PostMapping("/search")
    public User search(@RequestBody User user) {
        log.info("*** search方法已被请求执行...");
        // TODO
        user.setWxId(UUID.randomUUID().toString()); // 设置微信ID为随机字符
        return user;
    }

    // 登录接口
    @PostMapping("/login")
    public Object login(@RequestBody LoginRequestDto loginRequestDto) {
        // 1、根据账号、密码验证用户身份
        Map<String, Object> response = new HashMap<>();
        User user = userService.login(loginRequestDto.getUserName(), loginRequestDto.getPw());
        if (null == user) {
            response.put("code", -1001);
            response.put("message", "用户登录失败");
            return response;
        } else {
            // 2、生成登录令牌Token
            Map<String, Object> data = new HashMap<>();
            data.put("user_id", user.getId());
            data.put("user_name", user.getWxId());
            data.put("role", user.getRoles());
            String token = jwtOperator.generateToken(data);

            response.put("code", 1000);
            response.put("message", "用户登录成功");
            response.put("data", LoginResponseDto.builder()
                    .userId(user.getId())
                    .userName(user.getWxId())
                    .nickName(user.getWxNickname())
                    .role(user.getRoles())
                    .avatar(user.getAvatarUrl())
                    .expire(jwtOperator.getExpirationTime().getTime())
                    .token(token)
                    .build());
            return response;
        }
    }
}
