package com.gini.controller.request;


import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class PartRequest {

    @NotNull
    private String partName;

    @NotNull

    private String partNumber;

   // @ValidPartCount
    private String partCount;

    @NotNull
    @Valid //to validate the fields inside nested object we need this annotation. Otherwise it doesn't work.
    private PriceRequest partPrice;

    private CarModelRequest carModel;

    @NotNull
    private String suplayerName;
    private String manufacturer;

}
