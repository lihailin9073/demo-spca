package com.wzliulan.demo.spca.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDto {
    private Integer userId; // 用户ID
    private String userName; // 账号
    private String avatar; // 头像
    private String nickName; // 昵称
    private String role; // 角色
    private String token; // 登录令牌
    private Long expire; // 过期时间
}
