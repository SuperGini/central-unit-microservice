package com.gini.controller;

import com.gini.controller.request.PartRequest;
import com.gini.controller.request.UpdatePartRequest;
import com.gini.repositories.response.FindPartResponse;
import com.gini.repositories.response.ListPartsResponse;
import com.gini.repositories.response.PartResponse;
import com.gini.service.services.PartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class PartController {

    private final PartServiceImpl partService;


    @PostMapping("/parts")
    public ResponseEntity<PartResponse> createPart(@RequestBody PartRequest partRequest) {

        PartResponse response = partService.createPart(partRequest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("/parts/{pageNumber}")
    public ResponseEntity<List<ListPartsResponse>> findAllPartsWithPagination(@PathVariable String pageNumber) {
        var partsList = partService.findAllPartsWithPagination(pageNumber);

        return new ResponseEntity<>(partsList, HttpStatus.OK);
    }

    @GetMapping("/parts/count")
    public ResponseEntity<Integer> findPartsCount() {
        var partsCount = partService.findPartsCount();

        return new ResponseEntity<>(partsCount, HttpStatus.OK);
    }

    @PutMapping("/parts")
    public ResponseEntity<?> updatePart(@RequestBody UpdatePartRequest updatePartRequest) {
        Integer ok = partService.updatePart(updatePartRequest);

        return ResponseEntity.ok().body(ok);
    }

    @GetMapping("/parts/part/{partNumber}")
    public ResponseEntity<FindPartResponse> findPartByPartNumber(@PathVariable String partNumber) {

        return ResponseEntity
                .ok()
                .body(partService.findPartByPartNumber(partNumber));
    }

}
