package com.gini.config;

import com.gini.controller.request.PartRequest;
import com.gini.controller.response.PartResponse;
import com.gini.service.feign.FeignClientInventory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FallBackWarehouse implements FallbackFactory<FeignClientInventory> {

    @Override
    public FeignClientInventory create(Throwable cause) {
        log.info("************************************************************");
        return new FeignClientInventory() {
            @Override
            public ResponseEntity<PartResponse> createPart(PartRequest request) {
                log.info("************************************************************");

                PartResponse x = PartResponse.builder()
                        .partSpecification("xxxxxxxxxxxxxxx")
                        .build();

                return new ResponseEntity<>(x, HttpStatus.OK);
            }
        };

    }
}
