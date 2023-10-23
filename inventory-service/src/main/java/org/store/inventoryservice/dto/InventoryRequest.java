package org.store.inventoryservice.dto;

public record InventoryRequest(
        String skuCode,
        Integer quantity
) {
}
