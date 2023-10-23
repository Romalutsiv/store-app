package org.store.inventoryservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import org.store.inventoryservice.dto.InventoryInStockResponse;
import org.store.inventoryservice.dto.InventoryRequest;
import org.store.inventoryservice.model.Inventory;

import java.util.List;

public interface InventoryService {

    /**
     * @param skuCode you want to check.
     * @return response with scuCode of item and boolean value which means if item in stock.
     */
    InventoryInStockResponse isInStoke(@NonNull final String skuCode);

    /**
     * @param skuCodes list of scuCodes.
     * @return list of InventoryInStockResponse.
     */
    List<InventoryInStockResponse> isInStokeList(@NonNull final List<String> skuCodes);

    /**
     * @param skuCode you want to find.
     * @return Inventory model you trying to find.
     * @throws EntityNotFoundException if model not found.
     */
    Inventory getByScuCode(@NonNull final String skuCode);

    /**
     * @param inventoryRequest scuCode and quantity information to creating.
     * @return new Inventory model.
     */
    Inventory create(@NonNull final InventoryRequest inventoryRequest);

    /**
     * @param inventoryRequest scuCode and quantity information to creating or reduce.
     * @return new or updating Inventory model.
     */
    Inventory reduceQuantity(@NonNull final InventoryRequest inventoryRequest);
    /**
     * @param inventoryRequest scuCode and quantity information to creating or increase.
     * @return new or updating Inventory model.
     */
    Inventory increaseQuantity(@NonNull final InventoryRequest inventoryRequest);

    /**
     * @param id inventory number to delete.
     */
    void deleteInventory(@NonNull final Long id);

    /**
     * @param requestList list with scuCodes and quantity information.
     * @return message with count of new inventories and count of updating inventories.
     */
    String createListOfInventory(@NonNull final List<InventoryRequest> requestList, @NonNull final String invoiceType);

}
