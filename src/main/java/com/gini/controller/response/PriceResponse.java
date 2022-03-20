package com.gini.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal price;
    private String currency;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal discount;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal vat;
}
