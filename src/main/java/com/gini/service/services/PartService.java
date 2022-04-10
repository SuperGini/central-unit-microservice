package com.gini.service.services;

import com.gini.controller.request.PartRequest;
import com.gini.controller.request.UpdatePartRequest;
import com.gini.controller.response.FindPartResponse;
import com.gini.controller.response.FindPartWithCurrencyResponse;
import com.gini.controller.response.ListPartsResponse;
import com.gini.controller.response.PartResponse;

import java.util.List;

public interface PartService {
    PartResponse createPart(PartRequest request);

    List<ListPartsResponse> findAllPartsWithPagination(String pageNumber);

    Integer findPartsCount();

    Integer updatePart(UpdatePartRequest updatePartRequest);

    FindPartWithCurrencyResponse findPartByPartNumber(String partNumber);
}
