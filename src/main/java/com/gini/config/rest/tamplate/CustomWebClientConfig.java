package com.gini.config.rest.tamplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class CustomWebClientConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public RestTemplate currencyClient(RestTemplateBuilder builder){
        return builder
                .rootUri("https://openexchangerates.org/api")
                .setConnectTimeout(Duration.ofSeconds(15))
                .setReadTimeout(Duration.ofSeconds(15))
                .errorHandler(errorHandler())
                .build();
    }

    @Bean
    public RestTemplateCurrencyErrorHandler errorHandler(){
        return new RestTemplateCurrencyErrorHandler(objectMapper);
    }

}
