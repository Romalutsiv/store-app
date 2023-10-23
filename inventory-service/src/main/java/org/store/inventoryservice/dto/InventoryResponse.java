package org.store.inventoryservice.dto;

public record InventoryResponse(
        Long id,
        String skuCode,
        Integer quantity
) {
}
