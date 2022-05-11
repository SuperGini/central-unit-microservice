package com.gini.repositories.response;

import com.gini.repositories.response.currency.api.CurrencyErrorResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
public class FindPartWithCurrencyResponse extends FindPartResponse {

    private String priceRON;
    private String priceEURO;
    private String priceUSD;

    private CurrencyErrorResponse currencyErrorResponse;


}
