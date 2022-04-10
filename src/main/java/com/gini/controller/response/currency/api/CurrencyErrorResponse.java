package com.gini.controller.response.currency.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CurrencyErrorResponse {

    private Boolean error;
    private int status;
    private String message;
    private String description;
}
