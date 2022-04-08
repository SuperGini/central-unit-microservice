package com.gini.service.currency;

import com.gini.controller.response.currency.api.CurrencyValuesResponse;

public interface CurrencyWebClientService {

    CurrencyValuesResponse getCurrencyRates();
}
