package com.gini.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdatePartRequest {

    @NotNull
    private String partId;

    @NotNull
    private String partName;

    @NotNull
    private String partNumber;

    @NotNull
    private String partCount;

    @NotNull
    private PriceRequest partPrice;

    private CarModelRequest carModel;

    @NotNull
    private SuplayerRequest suplayer;


}
