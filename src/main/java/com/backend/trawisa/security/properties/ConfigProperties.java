package com.backend.trawisa.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

//@Configuration
@ConfigurationProperties(prefix = "trawisa")
public class ConfigProperties {
    private String jwtRefreshToken;
    private String jwtSecretKey;
    private String awsAccessKey;
    private String awsSecretKey;
    private String awsRegion;
    private String awsBucket;

    // Getters and setters for each property

    public String getJwtRefreshToken() {
        return jwtRefreshToken;
    }

    public void setJwtRefreshToken(String jwtRefreshToken) {
        this.jwtRefreshToken = jwtRefreshToken;
    }

    public String getJwtSecretKey() {
        return jwtSecretKey;
    }

    public void setJwtSecretKey(String jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    public String getAwsAccessKey() {
        return awsAccessKey;
    }

    public void setAwsAccessKey(String awsAccessKey) {
        this.awsAccessKey = awsAccessKey;
    }

    public String getAwsSecretKey() {
        return awsSecretKey;
    }

    public void setAwsSecretKey(String awsSecretKey) {
        this.awsSecretKey = awsSecretKey;
    }

    public String getAwsRegion() {
        return awsRegion;
    }

    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }

    public String getAwsBucket() {
        return awsBucket;
    }

    public void setAwsBucket(String awsBucket) {
        this.awsBucket = awsBucket;
    }
}