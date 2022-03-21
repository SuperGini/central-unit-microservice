package com.gini.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryServerException extends RuntimeException {

    private int status;
    private String error;
    private String message;


    public InventoryServerException(String message, int status, String error) {
        super(message);
        this.status = status;
        this.error = error;
    }
}
