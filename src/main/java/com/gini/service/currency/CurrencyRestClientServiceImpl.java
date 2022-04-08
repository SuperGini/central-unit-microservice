package com.gini.service.currency;


import com.gini.controller.response.currency.api.CurrencyValuesResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service

public class CurrencyRestClientServiceImpl implements CurrencyWebClientService {

    @Value("${currency.id}")
    private String id;
    private final RestTemplate restTemplate;

    public CurrencyRestClientServiceImpl(@Qualifier("currencyClient") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public CurrencyValuesResponse getCurrencyRates() {
        return restTemplate.getForObject(String.format("/latest.json?app_id=%s", id), CurrencyValuesResponse.class);

    }


//    public CurrencyValuesResponse getCurrencyRates() {
//        return webClient.get()
//                .uri("/latest.json?app_id=060b60b3253743a8a641c844f8a63568")
//                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {return Mono.error(RuntimeException::new);})
//                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {return Mono.error(RuntimeException::new);})
//                .bodyToMono(CurrencyValuesResponse.class)
//                .onErrorMap(Predicate.not(RuntimeException.class::isInstance), throwable -> {return new IllegalArgumentException();})
//                .doOnError()
//                .block();
//
//    }


}
