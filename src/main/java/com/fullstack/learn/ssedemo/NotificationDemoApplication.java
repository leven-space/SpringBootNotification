package com.fullstack.learn.ssedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication
public class NotificationDemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(NotificationDemoApplication.class, args);
    }

}
