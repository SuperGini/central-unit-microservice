package com.gini.config;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StashServerException extends RuntimeException{

    private int status;
    private String message;
}
