package com.gini.service.feign;

import com.gini.controller.request.PartRequest;
import com.gini.controller.request.UpdatePartRequest;
import com.gini.controller.response.FindPartResponse;
import com.gini.controller.response.ListPartsResponse;
import com.gini.controller.response.PartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(name = "${warehouse.name}", url = "${warehouse.url}", configuration = FeignClientConfiguration.class)
public interface FeignClientInventory {


    @PostMapping("/v1/parts")
    ResponseEntity<PartResponse> createPart(@RequestBody PartRequest request);

    @GetMapping("/v1/parts/{pageNumber}")
    ResponseEntity<List<ListPartsResponse>> findAllPartsWithPagination(@PathVariable String pageNumber);

    @GetMapping("/v1/parts/count")
    ResponseEntity<Integer> findPartsCount();

    @PutMapping("/v1/parts")
    ResponseEntity<Integer> updatePart(@RequestBody UpdatePartRequest updatePartRequest);

    @GetMapping("/v1/parts/part/{partNumber}")
    ResponseEntity<FindPartResponse> findPartByPartNumber(@PathVariable String partNumber);

}
