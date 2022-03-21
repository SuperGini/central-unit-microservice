package com.gini.config;


import com.gini.error.handler.InventoryErrors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InventoryClientException extends RuntimeException {

    private int status;
    private String errorCode;
    private String errorMessage;
    private List<InventoryErrors> errors = new ArrayList<>();

    public InventoryClientException(int status, String errorCode, String errorMessage, List<InventoryErrors> errors) {
        super();
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errors = errors;
    }
}
