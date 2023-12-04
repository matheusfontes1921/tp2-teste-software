package com.seta.tis4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})

public class Tis4Application {
    public static void main(String[] args) {
        SpringApplication.run(Tis4Application.class, args);
    }
}
