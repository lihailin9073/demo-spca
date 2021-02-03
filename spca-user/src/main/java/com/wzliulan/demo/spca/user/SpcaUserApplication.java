package com.wzliulan.demo.spca.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import tk.mybatis.spring.annotation.MapperScan;

@EnableBinding({Sink.class})
@MapperScan("com.wzliulan.demo.spca.user.dao")
@SpringBootApplication
public class SpcaUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpcaUserApplication.class, args);
    }

}
