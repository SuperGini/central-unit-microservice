package com.gini.error.response;

public record InventoryErrors(
        String field,
        String message
) {
}
