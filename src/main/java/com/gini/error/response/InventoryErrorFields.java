package com.gini.error.response;

public record InventoryErrorFields(
        String field,
        String message
) {
}
