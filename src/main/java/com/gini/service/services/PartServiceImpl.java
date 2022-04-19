package com.gini.service.services;

import com.gini.controller.request.PartRequest;
import com.gini.controller.request.UpdatePartRequest;
import com.gini.convertor.Convertor;
import com.gini.error.exception.CurrencyClientError;
import com.gini.error.exception.CurrencyServerError;
import com.gini.error.exception.InventoryClientException;
import com.gini.error.exception.InventoryServerException;
import com.gini.mapper.PartDetailsDto;
import com.gini.repositories.PartDetailsRepository;
import com.gini.repositories.response.FindPartResponse;
import com.gini.repositories.response.FindPartWithCurrencyResponse;
import com.gini.repositories.response.ListPartsResponse;
import com.gini.repositories.response.PartResponse;
import com.gini.repositories.response.currency.api.CurrencyValuesResponse;
import com.gini.service.currency.CurrencyRestClientService;
import com.gini.service.feign.FeignClientInventory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PartServiceImpl implements PartService {

    private final static MathContext TWO_DIGITS_ROUND_HALF_UP = new MathContext(2, RoundingMode.HALF_UP);

    private final FeignClientInventory feignClientInventory;
    private final PartDetailsRepository partDetailsRepository;
    private final CurrencyRestClientService currencyWebClientService;

    @Override
    public PartResponse createPart(PartRequest request) {
        return feignClientInventory.createPart(request).getBody();
    }

    @Override
    public List<ListPartsResponse> findAllPartsWithPagination(String pageNumber) {
        return feignClientInventory.findAllPartsWithPagination(pageNumber).getBody();

    }

    @Override
    public Integer findPartsCount() {
        return feignClientInventory.findPartsCount().getBody();
    }

    @Override
    public Integer updatePart(UpdatePartRequest updatePartRequest) {
        return feignClientInventory.updatePart(updatePartRequest).getBody();
    }

    @Override
    public FindPartResponse updatePartPrice(String parNumber, String partPrice){
        return feignClientInventory.updatePartPrice(partPrice, partPrice).getBody();
    }

    @Override
    public FindPartWithCurrencyResponse findPartByPartNumber(String partNumber) {

        PartDetailsDto partDetailsDto = new PartDetailsDto();
        partDetailsDto.setPartNumber(partNumber);

        CurrencyValuesResponse response = null;
        FindPartResponse partResponse = null;
        try {

            partResponse = feignClientInventory.findPartByPartNumber(partNumber).getBody();
            partDetailsDto.toPartDetailsDto(partResponse);


        } catch (InventoryClientException e) {
            log.debug("warehouse: errorCode: {}, errorMessage: {} ", e.getErrorCode(), e.getErrorMessage());
            partDetailsDto.withClientException(e);
            saveToMongoDB(partDetailsDto);
            throw new InventoryClientException(e);

        } catch (InventoryServerException e) {
            log.debug("warehouse: errorCode: {}, errorMessage: {} ", e.getError(), e.getMessage());
            partDetailsDto.withServerException(e);
            saveToMongoDB(partDetailsDto);
            throw new InventoryServerException(e);

        } catch (Exception e) {
            //todo: circuit breaker? fall of method?
            throw new RuntimeException("warehouse service can't be accessed -> BUMMMMMMMMMMMMMMMMMMMMMMMMMMMMM :DDDDD");
        }

        try {

            response = currencyWebClientService.getCurrencyRates();
            partDetailsDto.partDetailsDto(response);

        } catch (CurrencyClientError e) {
            log.error("currency error message: {} ", e.getMessage());
            partDetailsDto.withCurrencyClientException(e);

        } catch (CurrencyServerError e) {
            log.error("currency error message: {} ", e.getMessage());
            partDetailsDto.withCurrencyServerError(e);
        }


        if (partDetailsDto.getError() == null) {
            setPartPricesInRONinUSDinEUR(partDetailsDto, response, partResponse);
            partDetailsDto.setBasePrice(null);
            partDetailsDto.setCurrency(null);
        }


        saveToMongoDB(partDetailsDto);
        return Convertor.convertToFindPartWithCurrencyResponse(partDetailsDto);
    }

    private void setPartPricesInRONinUSDinEUR(PartDetailsDto partDetailsDto, CurrencyValuesResponse response, FindPartResponse partResponse) {
        switch (partResponse.getCurrency()) {
            case "RON" -> {
                partDetailsDto.setPriceUSD(getPriceUSDfromRON(response, partResponse));
                partDetailsDto.setPriceEURO(getPriceEUROfromRON(response, partResponse));
                partDetailsDto.setPriceRON(partResponse.getPrice().toString());
            }
            case "USD" -> {
                partDetailsDto.setPriceUSD(partResponse.getPrice().toString());
                partDetailsDto.setPriceEURO(getPriceEUROfromUSD(response, partResponse));
                partDetailsDto.setPriceRON(getPriceRONfromUSD(response, partResponse));
            }
            case "EUR" -> {
                partDetailsDto.setPriceUSD(getPriceUSDfromEUR(response, partResponse));
                partDetailsDto.setPriceEURO(partResponse.getPrice().toString());
                partDetailsDto.setPriceRON(getPriceRONfromEUR(response, partResponse));
            }
        }
    }

    private void saveToMongoDB(PartDetailsDto partDetailsDto) {
        partDetailsRepository
                .save(Convertor.convertToPartDetails(partDetailsDto));
    }

    private String getPriceRONfromEUR(CurrencyValuesResponse response, FindPartResponse partResponse) {
        return partResponse.getPrice()
                .multiply(
                        response.getRates().getEUR()
                                .multiply(
                                        response.getRates().getRON(), TWO_DIGITS_ROUND_HALF_UP))
                .toString();
    }

    private String getPriceUSDfromEUR(CurrencyValuesResponse response, FindPartResponse partResponse) {
        return partResponse.getPrice()
                .multiply(
                        response.getRates().getEUR(), TWO_DIGITS_ROUND_HALF_UP)
                .toString();
    }

    private String getPriceRONfromUSD(CurrencyValuesResponse response, FindPartResponse partResponse) {
        return partResponse.getPrice()
                .multiply(
                        response.getRates().getRON(), TWO_DIGITS_ROUND_HALF_UP)
                .toString();
    }

    private String getPriceEUROfromUSD(CurrencyValuesResponse response, FindPartResponse partResponse) {
        return partResponse.getPrice()
                .multiply(
                        response.getRates().getEUR(), TWO_DIGITS_ROUND_HALF_UP)
                .toString();
    }

    private String getPriceEUROfromRON(CurrencyValuesResponse response, FindPartResponse partResponse) {
        return (partResponse.getPrice()
                .multiply(
                        response.getRates().getEUR()))
                .divide(response.getRates().getRON(), 2, RoundingMode.HALF_EVEN)
                .toString();
    }

    private String getPriceUSDfromRON(CurrencyValuesResponse response, FindPartResponse partResponse) {
        return partResponse.getPrice()
                .divide
                        (response.getRates().getRON(), 2, RoundingMode.HALF_EVEN)
                .toString();
    }


}
