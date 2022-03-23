package com.gini.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

public class CustomCircuitBreaker {


    @Bean
    public CircuitBreakerConfig customConfig() {

        return CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .slowCallRateThreshold(100)
                .slowCallDurationThreshold(Duration.ofMillis(60000))
                .permittedNumberOfCallsInHalfOpenState(2)
                .waitDurationInOpenState(Duration.ofMillis(10000))
                .minimumNumberOfCalls(2)
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED)
                .slidingWindowSize(5)
                .recordExceptions(IOException.class, TimeoutException.class)
                .build();
    }

    @Bean
    public CircuitBreakerRegistry customRegistry(CircuitBreakerConfig config) {

        return CircuitBreakerRegistry.of(config);
    }


    @Bean
    public CircuitBreaker xxx(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker("xxx", "customConfig");
    }
}
