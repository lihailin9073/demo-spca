package com.wzliulan.demo.spca.content.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ShareDto {
    private Integer id;

    private Integer userId;

    private String title;

    private String author;

    private String cover;

    private String summary;

    private BigDecimal price;

    private String downloadUrl;

    private Integer buyCount;

    private Byte isOriginal;

    private String auditStatus;

    private String reason;

    private Byte showFlag;

    private Date createTime;

    private Date updateTime;

    private String wxNickName;

}