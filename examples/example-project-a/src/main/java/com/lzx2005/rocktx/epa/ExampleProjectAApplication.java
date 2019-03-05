package com.lzx2005.rocktx.epa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ExampleProjectAApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleProjectAApplication.class, args);
    }
}
