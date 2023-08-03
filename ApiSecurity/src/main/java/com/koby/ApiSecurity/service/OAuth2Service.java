package com.koby.ApiSecurity.service;

import com.koby.ApiSecurity.service.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OAuth2Service {
    private static final Logger logger = LoggerFactory.getLogger(OAuth2Service.class);

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public OAuth2Service(OAuth2AuthorizedClientService authorizedClientService, JwtTokenService jwtTokenService) {
        logger.info("OAuth2Service method reached");

        this.authorizedClientService = authorizedClientService;
        this.jwtTokenService = jwtTokenService;
    }

    public String validateAndPerformAction(String userName) {
        logger.info("Validating and performing action for user: {}", userName);

        logger.info("Loading OAuth2 client for user: {}", userName);
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient("github", userName);
        logger.info("Loaded OAuth2AuthorizedClient: {}", client);

        logger.info("Generating JWT token for user: {}", userName);
        String jwtToken = jwtTokenService.generateToken(userName);
        logger.info("Generated JWT token: {}", jwtToken);


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        logger.info("Sending request to validate token");
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8081/validateToken", HttpMethod.GET, entity, String.class);
        logger.info("ResponseEntity: {}", responseEntity);

        logger.info("Response from UserManagementService: {}", responseEntity.getBody());

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            logger.info("Request Performed Successfully!!");
            // Perform the request
            return "Request performed successfully!";
        } else {
            // Return 401 status
            return "Authorization error!";
        }
    }
}
