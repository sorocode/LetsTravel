package com.letsTravel.LetsTravel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class TestComponent {

    @Value("${spring.datasource.username}")
    public String initValue;
    
    public void printA() {
        System.out.println(initValue);
    }
}
