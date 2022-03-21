package com.gini.error.handler;


import com.gini.config.InventoryClientException;
import com.gini.config.InventoryServerException;
import feign.FeignException;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


@RestControllerAdvice
public class FeignExceptionHandler {

        //i keep this method because is gooooooooooooooooooooood:D!!!!!
//    @ExceptionHandler(FeignException.BadRequest.class)
//    public Map<String, Object> handleFeignStatusException(FeignException e, HttpServletResponse response) {
//        response.setStatus(e.status());
//        return new JSONObject(e.contentUTF8()).toMap();
//    }

    @ExceptionHandler(InventoryClientException.class)
    public ResponseEntity<RestErrorResponse> handleFeignStatusException(InventoryClientException e) {

        RestErrorResponse response = new RestErrorResponse(
                e.getErrorCode(),
                e.getErrorMessage(),
                e.getErrors()
        );

        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getStatus()));
    }

    @ExceptionHandler(InventoryServerException.class)
    public ResponseEntity<RestErrorResponse> handleServerErrors(InventoryServerException e){

        RestErrorResponse response = new RestErrorResponse(
                e.getErrorCode(),
                e.getErrorMessage(),
                List.of() //just something here
        );

        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getStatus()));
    }
}
