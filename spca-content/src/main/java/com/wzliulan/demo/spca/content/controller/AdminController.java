package com.wzliulan.demo.spca.content.controller;

import com.wzliulan.demo.spca.content.auth.PermissionAuth;
import com.wzliulan.demo.spca.content.domain.dto.ShareAuditDto;
import com.wzliulan.demo.spca.content.domain.model.Share;
import com.wzliulan.demo.spca.content.service.ShareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("/admin")
@RestController
public class AdminController {
    @Autowired
    private ShareService shareService;

    //@PermissionAuth("admin")
    @PutMapping("/audit-share/{shareId}")
    public Object auditShare(@PathVariable Integer shareId, @RequestBody ShareAuditDto auditDto) {
        // TODO 认证、鉴权
        Share share = shareService.auditShare(shareId, auditDto);
        return share;
    }

}
