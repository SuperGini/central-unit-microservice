package com.gini.config.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gini.error.exception.CurrencyClientError;
import com.gini.error.exception.CurrencyServerError;
import com.gini.error.exception.InventoryClientException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@RequiredArgsConstructor
public class RestTemplateCurrencyErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (
                response.getStatusCode().series() == CLIENT_ERROR
                        || response.getStatusCode().series() == SERVER_ERROR);
    }


    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        if (response.getStatusCode().is4xxClientError()) {
            String errorMessage = IOUtils.toString(response.getBody(), String.valueOf(StandardCharsets.UTF_8));

            throw  objectMapper.readValue(errorMessage, CurrencyClientError.class);
        }

        if (response.getStatusCode().is5xxServerError()) {
            throw new CurrencyServerError(response.getStatusCode().value());
        }


    }
}
