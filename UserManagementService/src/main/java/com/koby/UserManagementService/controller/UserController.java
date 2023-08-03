package com.koby.UserManagementService.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.koby.UserManagementService.service.TokenValidationService;

@RestController
public class UserController {

    @Autowired
    private TokenValidationService tokenValidationService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @GetMapping("/validateToken")
    public String validateToken(@RequestHeader("Authorization") String token) {
        String jwtToken = token.replace("Bearer ", "");
        String validationStatus = tokenValidationService.validateToken(jwtToken);

        logger.info("Validation Status: {}", validationStatus);
        return validationStatus;
    }
}
