package com.gini.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryServerException extends RuntimeException{

    private int status;
    private String errorCode;
    private String errorMessage;
}
