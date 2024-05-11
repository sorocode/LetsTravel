package com.letsTravel.LetsTravel.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {

    @Value("${spring.datasource.username}")
    private String username;

    public String getPath() {
		return username;
    }
}