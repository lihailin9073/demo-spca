package com.wzliulan.demo.spca.content.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

// 学习用的测试控制器
@Slf4j
@RequestMapping("/test")
@RestController
public class TestController {

    // 测试Spring Cloud Stream
    @Autowired
    private Source source;
    @GetMapping("/stream-send")
    public Object streamSendMsg() {
        // 1、构造消息
        Message message = MessageBuilder.withPayload("这是消息体, send_time="+ UUID.randomUUID().toString()).build();
        // 2、发送消息
        source.output().send(message);

        return "success";
    }

}
