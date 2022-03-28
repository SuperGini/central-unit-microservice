package com.gini.service.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.TimeUnit;

/**
 * Feign will treat any 3xx status as errors -> bug
 **/
public record FeignClientConfiguration(
        ObjectMapper objectMapper
) {

    private static final int CONNECT_TIMOUT = 15;
    private static final int READ_TIMEOUT = 15;

    @Bean
    public ErrorDecoder errorDecoder(){
        return new CustomErrorDecoder(objectMapper);
    }

    @Bean
    public Request.Options connectAndReadTimeouts() {
        return new Request.Options(
                CONNECT_TIMOUT, TimeUnit.SECONDS,
                READ_TIMEOUT, TimeUnit.SECONDS,
                true);
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
