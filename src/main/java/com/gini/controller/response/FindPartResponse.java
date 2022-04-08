package com.gini.controller.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
public class FindPartResponse {

    private UUID id;
    private String partName;
    private Integer partCount;
    private String partNumber;
    private BigDecimal price;
    private String currency;
    private String manufacturer;
}
