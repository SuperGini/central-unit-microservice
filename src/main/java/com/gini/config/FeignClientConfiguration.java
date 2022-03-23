package com.gini.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gini.service.feign.FeignClientInventory;
import feign.Feign;
import feign.Logger;
import feign.Request;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;
/**
 Feign will treat any 3xx status as errors -> bug
 **/
public record FeignClientConfiguration(
        ObjectMapper objectMapper
) {

    private static final int CONNECT_TIMOUT = 5;
    private static final int READ_TIMEOUT = 5;

    @Bean
    public Feign.Builder myClient() {
        return Feign.builder()
                .errorDecoder(new CustomErrorDecoder(objectMapper))
                .options(connectAndReadTimeouts())
                .logLevel(Logger.Level.BASIC);
    }

    private Request.Options connectAndReadTimeouts() {
        return new Request.Options(
                CONNECT_TIMOUT, TimeUnit.SECONDS,
                READ_TIMEOUT, TimeUnit.SECONDS,
                true);
    }

}
