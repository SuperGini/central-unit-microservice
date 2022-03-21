package com.gini.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class FeignClientConfiguration {

    @Bean
    public ErrorDecoder errorDecoder(ObjectMapper objectMapper){
        return new CustomErrorDecoder(objectMapper);
    }
}
