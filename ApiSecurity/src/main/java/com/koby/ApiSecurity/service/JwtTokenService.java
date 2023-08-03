package com.koby.ApiSecurity.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenService {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenService.class);


    //@Value("${jwt.secret}")
    private String secretKey = "ThisIsASecretKeyForJwtTokenWithAtLeast256Bits"; //wouldnt be hardcoded in a prod env


    public String generateToken(String username) {
        logger.info("Secret key: {}", secretKey);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 9000); // 9000 = 15 minutes

        logger.info("Generating JWT token for user: {}", username);

        io.jsonwebtoken.JwtBuilder builder = Jwts.builder();
        builder.setSubject(username);
        builder.setIssuedAt(now);
        builder.setExpiration(expiryDate);
        builder.signWith(SignatureAlgorithm.HS256, secretKey);
        String jwtToken = builder
                .compact();

        logger.info("Generated JWT token: {}", jwtToken);
        return jwtToken;
    }
}
