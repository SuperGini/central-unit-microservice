package com.gini.error.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CurrencyClientError extends RuntimeException{

    private Boolean error;
    private int status;
    private String message;
    private String description;

}
