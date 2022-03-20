package com.gini.service.feign;

import com.gini.config.FeignClientConfiguration;
import com.gini.controller.request.PartRequest;
import com.gini.controller.response.PartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory", url = "localhost:8080")
public interface FeignClientInventory {

    @PostMapping("/v1/parts")
    ResponseEntity<PartResponse> createPart(@RequestBody PartRequest request);




}
