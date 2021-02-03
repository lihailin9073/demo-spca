package com.wzliulan.demo.spca.user.domain.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddBonusMsgDto {
    private Integer userId; // 用户ID
    private Integer bonus; // 积分数
}
