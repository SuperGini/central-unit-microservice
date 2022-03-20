package com.gini.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import static feign.FeignException.errorStatus;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {


//        @Override
//        public Exception decode(String methodKey, Response response) {
//            if (response.status() >= 400 && response.status() <= 499) {
//                return new StashClientException(
//                        response.status(),
//                        response.reason()
//                );
//            }
//            if (response.status() >= 500 && response.status() <= 599) {
//                return new StashServerException(
//                        response.status(),
//                        response.reason()
//                );
//            }
//            return errorStatus(methodKey, response);
        return null;
       }

}
