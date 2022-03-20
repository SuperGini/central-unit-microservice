package com.gini.controller.request;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CarModelRequest {

    @NotNull
    private String constructor;

    @NotNull
    private String model;

    private String year;

    private String engine;

}
