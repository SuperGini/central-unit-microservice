package com.gini.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gini.error.exception.InventoryClientException;
import com.gini.error.exception.InventoryServerException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


import static feign.FeignException.errorStatus;

@Slf4j
public record CustomErrorDecoder(ObjectMapper objectMapper) implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() >= 400 && response.status() <= 499) {

            try {
                String errorMessage = IOUtils.toString(response.body().asInputStream(), String.valueOf(StandardCharsets.UTF_8));

                InventoryClientException clientException = objectMapper.readValue(errorMessage, InventoryClientException.class);
                clientException.setStatus(response.status());

                throw clientException;
            } catch (IOException e) {
                log.error("Error reading client error message from inventory: ", e);
            }

        }
        if (response.status() >= 500 && response.status() <= 599) {

            try {
                String errorMessage = IOUtils.toString(response.body().asInputStream(), String.valueOf(StandardCharsets.UTF_8));

                InventoryServerException serverException = objectMapper.readValue(errorMessage, InventoryServerException.class);
                serverException.setStatus(response.status());

                return serverException;
            } catch (IOException e) {
                log.error("Error reading server error message from inventory: ", e);
            }
        }
        return errorStatus(methodKey, response);

    }

}
