package com.koby.UserManagementService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public String secretKey() {
        return "ThisIsASecretKeyForJwtTokenWithAtLeast256Bits"; //i would not hardcode in production of course
    }
}
