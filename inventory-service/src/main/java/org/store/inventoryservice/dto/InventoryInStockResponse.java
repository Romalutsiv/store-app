package org.store.inventoryservice.dto;

public record InventoryInStockResponse(
        String scuCode,
        boolean inStock
) {
}
