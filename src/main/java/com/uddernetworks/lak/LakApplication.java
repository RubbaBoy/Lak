package com.uddernetworks.lak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com"})
public class LakApplication {

    public static void main(String[] args) {
        SpringApplication.run(LakApplication.class, args);
    }

}
