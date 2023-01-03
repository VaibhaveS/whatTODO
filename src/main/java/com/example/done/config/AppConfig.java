package com.example.done.config;

import com.example.done.Signature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean("sig1")
    public Signature createSign(){
        return new Signature("value 1");
    }
}
