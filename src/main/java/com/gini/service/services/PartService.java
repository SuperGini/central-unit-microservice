package com.gini.service.services;

import com.gini.controller.request.PartRequest;
import com.gini.controller.request.UpdatePartRequest;
import com.gini.repositories.response.FindPartResponse;
import com.gini.repositories.response.FindPartWithCurrencyResponse;
import com.gini.repositories.response.ListPartsResponse;
import com.gini.repositories.response.PartResponse;

import java.util.List;

public interface PartService {
    PartResponse createPart(PartRequest request);

    List<ListPartsResponse> findAllPartsWithPagination(String pageNumber);

    Integer findPartsCount();

    Integer updatePart(UpdatePartRequest updatePartRequest);

    FindPartResponse updatePartPrice(String parNumber, String partPrice);

    FindPartWithCurrencyResponse findPartByPartNumber(String partNumber);
}
