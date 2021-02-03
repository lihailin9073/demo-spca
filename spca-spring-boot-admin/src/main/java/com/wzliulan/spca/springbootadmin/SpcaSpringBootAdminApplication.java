package com.wzliulan.spca.springbootadmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class SpcaSpringBootAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpcaSpringBootAdminApplication.class, args);
    }

}
