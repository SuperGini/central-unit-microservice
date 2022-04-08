package com.gini.config.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

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








    @Bean
    public WebClient currencyWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl("https://openexchangerates.org/api")
                .clientConnector(new ReactorClientHttpConnector(timeouts()))
                .build();
    }

    @Bean
    public HttpClient timeouts() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));
    }
}
