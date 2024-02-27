package com.apex.tech3.wallt_app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Forum Application API Documentation", version = "10.0",
        description = "This is the documentation for our team project"))
public class WalltAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalltAppApplication.class, args);
    }

}
