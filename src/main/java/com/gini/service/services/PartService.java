package com.gini.service.services;

import com.gini.controller.response.ListPartsResponse;

import java.util.List;

public interface PartService {
    List<ListPartsResponse> findAllPartsWithPagination(String pageNumber);

    Integer findPartsCount();
}
