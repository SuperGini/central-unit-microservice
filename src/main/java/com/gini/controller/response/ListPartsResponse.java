package com.gini.controller.response;

import lombok.Data;

import java.util.UUID;

@Data
public class ListPartsResponse {

    private UUID id;
    private String partName;
    private String partCount;
    private String partNumber;
    private PriceResponse price;
    private String partSpecification;
    private String manufacturer;
    private String totalNumberOfParts;
    private String totalNumberOfPages;
}
