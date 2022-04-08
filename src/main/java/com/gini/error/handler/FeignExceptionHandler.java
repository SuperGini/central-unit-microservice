package com.gini.error.handler;


import com.gini.error.exception.InventoryClientException;
import com.gini.error.exception.InventoryServerException;
import com.gini.error.response.RestErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class FeignExceptionHandler extends ResponseEntityExceptionHandler {

    //i keep this method because is gooooooooooooooooooooood:D!!!!!
//    @ExceptionHandler(FeignException.BadRequest.class)
//    public Map<String, Object> handleFeignStatusException(FeignException e, HttpServletResponse response) {
//        response.setStatus(e.status());
//        return new JSONObject(e.contentUTF8()).toMap();
//    }

    @ExceptionHandler(InventoryClientException.class)
    public ResponseEntity<RestErrorResponse> handleWarehouseException(InventoryClientException e) {
        log.warn("Error -> errorCode: {} -- errorMessage: {} -- errors: {}", e.getErrorCode(), e.getErrorMessage(), e.getErrors(), e);
        RestErrorResponse response = new RestErrorResponse(
                e.getErrorCode(),
                e.getErrorMessage(),
                e.getErrors()
        );

        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getStatus()));
    }

    @ExceptionHandler(InventoryServerException.class)
    public ResponseEntity<RestErrorResponse> handleServerErrors(InventoryServerException e) {
        log.error("Error: ", e);
        RestErrorResponse response = new RestErrorResponse(
                e.getError(),
                e.getMessage(),
                List.of() //just something here
        );

        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getStatus()));
    }
}
