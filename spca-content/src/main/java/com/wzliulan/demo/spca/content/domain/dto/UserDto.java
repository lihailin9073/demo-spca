package com.wzliulan.demo.spca.content.domain.dto;

import lombok.Data;
import java.util.Date;

@Data
public class UserDto {
    private Integer id;

    private String wxId;

    private String wxNickname;

    private String roles;

    private String avatarUrl;

    private Integer bonus;

    private Date createTime;

    private Date updateTime;

}