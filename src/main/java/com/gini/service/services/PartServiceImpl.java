package com.gini.service.services;

import com.gini.controller.request.PartRequest;
import com.gini.controller.response.ListPartsResponse;
import com.gini.controller.response.PartResponse;
import com.gini.service.feign.FeignClientInventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PartServiceImpl implements PartService {

    private final FeignClientInventory feignClientInventory;

    @Override
    public PartResponse createPart(PartRequest request){
        return feignClientInventory.createPart(request).getBody();
    }

    @Override
    public List<ListPartsResponse> findAllPartsWithPagination(String pageNumber){
        return feignClientInventory.findAllPartsWithPagination(pageNumber).getBody();

    }

    @Override
    public Integer findPartsCount(){
        return feignClientInventory.findPartsCount().getBody();
    }


}
