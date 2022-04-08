package com.gini.mapper;

import com.gini.controller.response.FindPartResponse;
import com.gini.controller.response.currency.api.CurrencyValuesResponse;
import com.gini.error.exception.CurrencyClientError;
import com.gini.error.exception.CurrencyServerError;
import com.gini.error.exception.InventoryClientException;
import com.gini.error.exception.InventoryServerException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PartDetailsDto {

    private String partId;
    private String partName;
    private String partCount;
    private String partNumber;
    private String currency;
    private String manufacturer;
    private String priceRON;
    private String priceEURO;
    private String priceUSD;
    private String ratingRON;
    private String ratingEURO;
    private String ratingUSD;
    private int status;
    private String errorCode;
    private String errorMessage;
    private Boolean error;
    private String message;
    private String description;

    public PartDetailsDto toPartDetailsDto(FindPartResponse partResponse) {

        setPartId(partResponse.getId().toString());
        setPartName(partResponse.getPartName());
        setPartCount(partResponse.getPartCount().toString());
        setPartNumber(partResponse.getPartNumber());
        setCurrency(partResponse.getCurrency());
        setManufacturer(partResponse.getManufacturer());
        return this;
    }


    public PartDetailsDto partDetailsDto(CurrencyValuesResponse currencyResponse) {
        setRatingEURO(currencyResponse.getRates().getEUR().toString());
        setRatingUSD(currencyResponse.getRates().getUSD().toString());
        setRatingRON(currencyResponse.getRates().getRON().toString());
        return this;
    }

    public PartDetailsDto withClientException(InventoryClientException e) {
        setStatus(e.getStatus());
        setErrorCode(e.getErrorCode());
        setErrorMessage(e.getErrorMessage());
        return this;
    }

    public PartDetailsDto withServerException(InventoryServerException e) {
        setStatus(e.getStatus());
        setErrorCode(e.getError());
        setErrorMessage(e.getMessage());
        return this;
    }

    public PartDetailsDto withCurrencyClientException(CurrencyClientError e){
        setError(e.getError());
        setStatus(e.getStatus());
        setMessage(e.getMessage());
        setDescription(e.getDescription());
        return this;
    }

    public PartDetailsDto withCurrencyServerError(CurrencyServerError e){
        setStatus(e.getStatus());
        return this;
    }


}
