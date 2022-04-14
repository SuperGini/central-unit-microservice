package com.gini.convertor;

import com.gini.repositories.response.FindPartWithCurrencyResponse;
import com.gini.repositories.response.currency.api.CurrencyErrorResponse;
import com.gini.entities.PartDetails;
import com.gini.mapper.PartDetailsDto;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@UtilityClass
public class Convertor {

    private static final ZoneOffset UTC = ZoneOffset.UTC;
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyy");

    public PartDetails convertToPartDetails(PartDetailsDto dto) {
        return PartDetails.builder()
                .id(UUID.randomUUID().toString())
                .partId(dto.getPartId())
                .currency(dto.getCurrency())
                .partCount(dto.getPartCount())
                .partName(dto.getPartName())
                .partNumber(dto.getPartNumber())
                .manufacturer(dto.getManufacturer())
                .priceEURO(dto.getPriceEURO())
                .priceUSD(dto.getPriceUSD())
                .priceRON(dto.getPriceRON())
                .ratingEURO(dto.getRatingEURO())
                .ratingUSD(dto.getRatingUSD())
                .ratingRON(dto.getRatingRON())
                .status(dto.getStatus())
                .errorCode(dto.getErrorCode())
                .errorMessage(dto.getErrorMessage())
                .error(dto.getError())
                .message(dto.getMessage())
                .description(dto.getDescription())
                .basePrice(dto.getBasePrice())
                .created(OffsetDateTime.now(UTC).format(format))
                .build();
    }

    public FindPartWithCurrencyResponse convertToFindPartWithCurrencyResponse(PartDetailsDto dto) {
        var partResponseWithCurrency = FindPartWithCurrencyResponse.builder()
                .id(UUID.fromString(dto.getPartId()))
                .currency(dto.getCurrency())
                .partCount(Integer.parseInt(dto.getPartCount()))
                .partName(dto.getPartName())
                .partNumber(dto.getPartNumber())
                .manufacturer(dto.getManufacturer())
                .priceEURO(dto.getPriceEURO())
                .priceUSD(dto.getPriceUSD())
                .priceRON(dto.getPriceRON())
                .currencyErrorResponse(CurrencyErrorResponse.builder()
                        .error(dto.getError())
                        .status(dto.getStatus())
                        .message(dto.getMessage())
                        .description(dto.getDescription())
                        .build())
                .build();

        Optional.ofNullable(dto.getError())
                .ifPresent(thenHasError -> setPartPriceAndCurrency(dto, partResponseWithCurrency));

        return partResponseWithCurrency;
    }


    private void setPartPriceAndCurrency(PartDetailsDto dto, FindPartWithCurrencyResponse partResponseWithCurrency) {
        partResponseWithCurrency.setCurrency(dto.getCurrency());
        partResponseWithCurrency.setPrice(new BigDecimal(dto.getBasePrice()));
    }

}
