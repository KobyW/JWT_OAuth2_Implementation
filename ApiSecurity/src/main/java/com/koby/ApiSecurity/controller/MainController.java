package com.koby.ApiSecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/")
    public String home() {
        return "Welcome to the home page! This is Assignment 4 completed by Koby Wood 041050080";
    }
}