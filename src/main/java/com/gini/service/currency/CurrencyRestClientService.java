package com.gini.service.currency;

import com.gini.repositories.response.currency.api.CurrencyValuesResponse;

public interface CurrencyRestClientService {

    CurrencyValuesResponse getCurrencyRates();
}
