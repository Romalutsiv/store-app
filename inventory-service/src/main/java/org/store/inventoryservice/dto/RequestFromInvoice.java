package org.store.inventoryservice.dto;

import java.util.List;

public record RequestFromInvoice(
        List<InventoryRequest> requestList,
        String invoiceType
) {
}
