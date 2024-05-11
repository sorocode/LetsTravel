package com.letsTravel.LetsTravel.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConfigServiceTest {

    @Autowired
    Config config;

    @Test
    void findActiveRootTest() {
        String root = "testid";
        Assertions.assertEquals(config.getPath(), root);
    }

}