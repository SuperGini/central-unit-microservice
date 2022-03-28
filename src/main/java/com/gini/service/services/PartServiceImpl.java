package com.gini.service.services;

import com.gini.controller.response.ListPartsResponse;
import com.gini.service.feign.FeignClientInventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PartServiceImpl implements PartService {

    private final FeignClientInventory feignClientInventory;

    @Override
    public List<ListPartsResponse> findAllPartsWithPagination(String pageNumber){
        return feignClientInventory.findAllPartsWithPagination(pageNumber).getBody();

    }

    @Override
    public Integer findPartsCount(){
        return feignClientInventory.findPartsCount().getBody();
    }


}
