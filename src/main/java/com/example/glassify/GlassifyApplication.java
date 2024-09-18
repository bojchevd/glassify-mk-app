package com.example.glassify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GlassifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlassifyApplication.class, args);
    }

}
