package com.alkemy.technical.test.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "application.security.jwt")
public record PropertiesConfig(String secretKey, long expiration, RefreshToken refreshToken) {
}
