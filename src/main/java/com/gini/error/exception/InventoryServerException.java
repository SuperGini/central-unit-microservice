package com.gini.error.exception;

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
        this.message = message;
        this.status = status;
        this.error = error;
    }

    public InventoryServerException(InventoryServerException e) {
        this.status = e.status;
        this.error = e.error;
        this.message = e.message;
    }
}
