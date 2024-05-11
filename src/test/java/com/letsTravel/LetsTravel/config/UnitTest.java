package com.letsTravel.LetsTravel.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UnitTest {

    @Autowired
    public TestComponent com;

    @Test
    void test() {
        com.printA();
    }

}
