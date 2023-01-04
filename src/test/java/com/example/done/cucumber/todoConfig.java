package com.example.done.cucumber;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class todoConfig {


    @Bean
    public TestRestTemplate restTesmplate() {
        return new TestRestTemplate();
    }



}