package com.gini.error.handler;


import com.gini.config.StashClientException;
import feign.FeignException;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@RestControllerAdvice
public class FeignExceptionHandler {


    @ExceptionHandler(FeignException.BadRequest.class)
    public Map<String, Object> handleFeignStatusException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        return new JSONObject(e.contentUTF8()).toMap();
    }

//    @ExceptionHandler(StashClientException.class)
//    public String handleFeignStatusExceptio(FeignException e, HttpServletResponse response) {
//        response.setStatus(e.status());
//        return "feignError";
//    }
}
