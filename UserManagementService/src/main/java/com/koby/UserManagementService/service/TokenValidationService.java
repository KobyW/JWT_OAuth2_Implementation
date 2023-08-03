package com.koby.UserManagementService.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenValidationService {
    private final String secretKey;

    private static final Logger logger = LoggerFactory.getLogger(TokenValidationService.class);


    @Autowired
    public TokenValidationService(String secretKey) {
        this.secretKey = secretKey;
    }

    public String validateToken(String token) {
        logger.debug("validateToken reached");

        try {
            Jws<Claims> jwsClaims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            boolean isTokenExpired = jwsClaims.getBody().getExpiration().before(new Date());

            if (isTokenExpired) {
                logger.debug("LOG: TOKEN EXPIRED");
                return "Token expired";
            } else {
                logger.debug("LOG: VALID TOKEN");
                return "Token valid - USER role";
            }
        } catch (JwtException e) {
            logger.debug("LOG: INVALID TOKEN");
            logger.error(e.getMessage(), e);

            return "Invalid token";
        }
    }
}
