package com.koby.ApiSecurity.config;

import com.koby.ApiSecurity.controller.OAuthController;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;

@Configuration
public class OAuth2ClientConfig {
    private static final Logger logger = LoggerFactory.getLogger(OAuth2ClientConfig.class);


    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.githubClientRegistration());
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(this.clientRegistrationRepository());
    }

    private ClientRegistration githubClientRegistration() {
        logger.debug("reached ClientRegistration");
        return ClientRegistration.withRegistrationId("github")
                .clientId("fc56a51b2e0176d5902d")
                .clientSecret("863dc69cab33b2663e0ff9fc9edca21ee1effeee")
                .tokenUri("https://github.com/login/oauth/access_token")
                .authorizationUri("https://github.com/login/oauth/authorize")
                .userInfoUri("https://api.github.com/user")
                .userNameAttributeName("login")
                .clientName("GitHub")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/github")
                .scope("read:user")
                .build();
    }

}
