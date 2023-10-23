package org.store.inventoryservice.dto;

public record InventoryRequest(
        String scuCode,
        Integer quantity
) {
}
