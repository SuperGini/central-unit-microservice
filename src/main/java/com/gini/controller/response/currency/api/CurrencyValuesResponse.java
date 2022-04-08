package com.gini.controller.response.currency.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyValuesResponse {

    private String timestamp;
    private String base;
    private Rates rates;

}
