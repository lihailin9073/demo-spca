package com.wzliulan.demo.spca.content.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestService {
    @SentinelResource("common")
    public Object common() {
        log.info("...common");
        return "...common";
    }
}
