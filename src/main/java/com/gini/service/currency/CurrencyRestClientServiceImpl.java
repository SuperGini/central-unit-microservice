package com.gini.service.currency;


import com.gini.repositories.response.currency.api.CurrencyValuesResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service

public class CurrencyRestClientServiceImpl implements CurrencyRestClientService {

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

}
