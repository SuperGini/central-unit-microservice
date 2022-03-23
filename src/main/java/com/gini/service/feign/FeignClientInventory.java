package com.gini.service.feign;

import com.gini.config.FallBackWarehouse;
import com.gini.config.FeignClientConfiguration;
import com.gini.controller.request.PartRequest;
import com.gini.controller.response.PartResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "${warehouse.name}", url = "${warehouse.url}", configuration = FeignClientConfiguration.class)
public interface FeignClientInventory {

    @PostMapping("/v1/parts")
    ResponseEntity<PartResponse> createPart(@RequestBody PartRequest request);


}
