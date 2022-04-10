package com.gini.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@Document("part_details")
public class PartDetails {

    @Id
    private String id;
    private String partId;
    private String partName;
    private String partCount;
    private String partNumber;
    private String currency;
    private String basePrice;
    private String manufacturer;
    private String priceRON;
    private String priceEURO;
    private String priceUSD;
    private String ratingRON;
    private String ratingEURO;
    private String ratingUSD;
    private int status;
    private String errorCode;
    private String errorMessage;
    private String created;
    private Boolean error;
    private String message;
    private String description;

}
