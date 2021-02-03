package com.wzliulan.demo.spca.content.domain.dto;

import com.wzliulan.demo.spca.content.domain.enums.AuditStatusEnum;
import lombok.Data;

@Data
public class ShareAuditDto {
    private AuditStatusEnum auditStatusEnum; // 审核状态
    private String reason; // 审核备注
}
