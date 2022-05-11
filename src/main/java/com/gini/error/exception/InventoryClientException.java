package com.gini.error.exception;


import com.gini.error.response.InventoryErrorFields;
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
    private List<InventoryErrorFields> errors = new ArrayList<>();


    public InventoryClientException(InventoryClientException e) {
        this.status = e.getStatus();
        this.errorCode = e.getErrorCode();
        this.errorMessage = e.errorMessage;
    }

    public InventoryClientException(int status, String errorCode, String errorMessage) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
