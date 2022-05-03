package com.gini.error.handler;

import com.gini.error.code.ErrorCodes;
import com.gini.error.response.RestErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
//@RestControllerAdvice
public class CentralUnitExceptionHandler {


//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<RestErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e){
//
//        RestErrorResponse response = new RestErrorResponse(
//                ErrorCodes.USER_NOT_FOUND_ERROR.toString(),
//                ErrorCodes.USER_NOT_FOUND_ERROR.getMessage(),
//                null
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
//
//    }



}
