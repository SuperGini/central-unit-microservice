package com.gini.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Logger;
import feign.Request;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;

import java.util.concurrent.TimeUnit;

/**
 * Feign will treat any 3xx status as errors -> bug
 **/
public record FeignClientConfiguration(
        ObjectMapper objectMapper,
        CircuitBreakerRegistry circuitBreakerRegistry,
        FallBackWarehouse fallBackWarehouse
)  implements FeignFormatterRegistrar {

    private static final int CONNECT_TIMOUT = 5;
    private static final int READ_TIMEOUT = 5;

    @Bean
    public Feign.Builder myClient() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("xxx");
        FeignDecorators decorators = FeignDecorators.builder()
                .withCircuitBreaker(circuitBreaker)
                //.withFallback(fallBackWarehouse)
                .withFallbackFactory( x -> new FallBackWarehouse())
                //.withFallbackFactory(FallBackWarehouse::new)
                .build();
        return Resilience4jFeign.builder(decorators)
                .errorDecoder(new CustomErrorDecoder(objectMapper))
                .options(connectAndReadTimeouts())
                .logLevel((Logger.Level.BASIC));


//        return Feign.builder()
//                .errorDecoder(new CustomErrorDecoder(objectMapper))
//                .options(connectAndReadTimeouts())
//                .logLevel(Logger.Level.BASIC);
    }

    private Request.Options connectAndReadTimeouts() {
        return new Request.Options(
                CONNECT_TIMOUT, TimeUnit.SECONDS,
                READ_TIMEOUT, TimeUnit.SECONDS,
                true);
    }


    @Override
    public void registerFormatters(FormatterRegistry registry) {

    }
}
