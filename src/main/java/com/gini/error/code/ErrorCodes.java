package com.gini.error.code;

import lombok.Getter;

@Getter
public enum ErrorCodes {

    CURRENCY_CLIENT_ERROR("currency client error. Can,t access currency rates"),
    CURRENCY_SERVER_ERROR("currency server error. Server is down");

    private final String message;

    ErrorCodes(String message) {
        this.message = message;
    }
}
