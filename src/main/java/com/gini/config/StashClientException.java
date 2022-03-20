package com.gini.config;

import feign.Response;
import lombok.AllArgsConstructor;


public class StashClientException extends RuntimeException {

    private int status;
    private String reason;
    private Response.Body body;

    public StashClientException(String message, int status, String reason, Response.Body body) {
        super(message);
        this.status = status;
        this.reason = reason;
        this.body = body;
    }
}
