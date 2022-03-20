package com.gini.controller;

import com.gini.controller.request.PartRequest;
import com.gini.controller.response.PartResponse;
import com.gini.service.feign.FeignClientInventory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2")
public record PartController(
        FeignClientInventory feignClientInventory
) {


    @PostMapping("/parts")
    public ResponseEntity<PartResponse> createPart(@RequestBody PartRequest partRequest){

        PartResponse response = feignClientInventory.createPart(partRequest).getBody();

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

}
