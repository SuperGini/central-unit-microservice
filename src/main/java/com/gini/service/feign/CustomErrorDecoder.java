package com.gini.service.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gini.error.exception.InventoryClientException;
import com.gini.error.exception.InventoryServerException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static feign.FeignException.errorStatus;

@Slf4j
public record CustomErrorDecoder(ObjectMapper objectMapper) implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus httpStatus = HttpStatus.valueOf(response.status());

        if (httpStatus.is4xxClientError()) {
            throwClientException(response);

        }

        if (httpStatus.is5xxServerError()) {
            throwServerException(response);
        }

        return errorStatus(methodKey, response);
    }

    private void throwClientException(Response response) {
        try {
            String errorMessage = IOUtils.toString(response.body().asInputStream(), String.valueOf(StandardCharsets.UTF_8));

            InventoryClientException clientException = objectMapper.readValue(errorMessage, InventoryClientException.class);
            clientException.setStatus(response.status());

            throw clientException;
        } catch (IOException e) {
            //todo: throw exception here
            log.error("Error reading client error message from inventory: ", e);
        }
    }

    private void throwServerException(Response response) {
        try {
            String errorMessage = IOUtils.toString(response.body().asInputStream(), String.valueOf(StandardCharsets.UTF_8));

            InventoryServerException serverException = objectMapper.readValue(errorMessage, InventoryServerException.class);
            serverException.setStatus(response.status());

            throw serverException;
        } catch (IOException e) {
            //todo: throw exception here
            log.error("Error reading server error message from inventory: ");
        }
    }

}
