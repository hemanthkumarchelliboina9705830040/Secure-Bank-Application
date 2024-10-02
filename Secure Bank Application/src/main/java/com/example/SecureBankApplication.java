package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity()
@EnableMethodSecurity(jsr250Enabled = true,securedEnabled = true)
public class SecureBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.example.SecureBankApplication.class, args);
    }

}
