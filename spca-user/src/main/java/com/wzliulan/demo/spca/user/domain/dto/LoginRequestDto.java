package com.wzliulan.demo.spca.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDto {
    private String userName; // 账号
    private String pw;      // 密码
    private String vcode; // 验证码
}
