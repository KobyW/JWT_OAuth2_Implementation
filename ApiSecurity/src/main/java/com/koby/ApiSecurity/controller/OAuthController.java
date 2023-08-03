package com.koby.ApiSecurity.controller;

import com.koby.ApiSecurity.service.OAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class OAuthController {

    private static final Logger logger = LoggerFactory.getLogger(OAuthController.class);

    private final OAuth2Service oAuth2Service;

    @Autowired
    public OAuthController(OAuth2Service oAuth2Service) {
        this.oAuth2Service = oAuth2Service;
    }

    @GetMapping("/login-success")
    public String handleOAuth2Authentication(Authentication authentication) {
        logger.info("handleOAuth2Authentication method reached");

        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        String username = principal.getAttribute("login");
        logger.info("Passing login field (username): {}", username);
        return oAuth2Service.validateAndPerformAction(username);
    }
}
