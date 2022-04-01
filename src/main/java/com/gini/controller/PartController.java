package com.gini.controller;

import com.gini.controller.request.PartRequest;
import com.gini.controller.response.ListPartsResponse;
import com.gini.controller.response.PartResponse;
import com.gini.service.feign.FeignClientInventory;
import com.gini.service.services.PartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class PartController {

    private final FeignClientInventory feignClientInventory;
    private final PartServiceImpl partService;


    @PostMapping("/parts")
    public ResponseEntity<PartResponse> createPart(@RequestBody PartRequest partRequest) {

        PartResponse response = feignClientInventory.createPart(partRequest).getBody();

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("/parts/{pageNumber}")
    public ResponseEntity<List<ListPartsResponse>> findAllPartsWithPagination(@PathVariable String pageNumber) {
        var partsList = partService.findAllPartsWithPagination(pageNumber);

        return new ResponseEntity<>(partsList, HttpStatus.OK);
    }

    @GetMapping("/parts/count")
    public ResponseEntity<Integer> findPartsCount(){
        var partsCount = partService.findPartsCount();

        return new ResponseEntity<>(partsCount, HttpStatus.OK);
    }

}
