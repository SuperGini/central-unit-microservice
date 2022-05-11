package com.gini.repositories.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.UUID;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;
    private String partName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigInteger partCount;
    private String partNumber;
    private PriceResponse partPrice;
    private String partSpecification;
    private String manufacturer;
    private String status;



}
