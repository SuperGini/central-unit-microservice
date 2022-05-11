package com.gini.repositories.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public class FindPartResponse {

    private UUID id;
    private String partName;
    private Integer partCount;
    private String partNumber;
    private BigDecimal price;
    private String currency;
    private String manufacturer;
}
