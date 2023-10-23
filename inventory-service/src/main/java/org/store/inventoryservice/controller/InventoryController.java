package org.store.inventoryservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.store.inventoryservice.dto.RequestFromInvoice;
import org.store.inventoryservice.service.InventoryService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    /**
     * Inject inventoryService.
     */
    private final InventoryService inventoryService;

    /**
     * Endpoint for /api/inventory get method with required param for getting skuCode and inStock status.
     *
     * @param skuCode list of skuCodes
     * @return list of InventoryInStockResponse.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<?> isInStockList(@RequestParam(name = "skuCode") List<String> skuCode){
        return inventoryService.isInStokeList(skuCode);
    }

    /**
     * Endpoint for /api/inventory post method with required param for creating and updating quantity of items with scuCode.
     * Speak with accounting-service.
     *
     * @param request with InventoryRequest list and invoice type.
     * @return message how many items was created and updated.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody RequestFromInvoice request){
        return inventoryService.createListOfInventory(request.requestList(), request.invoiceType());
    }

    /**
     * Endpoint for /api/inventory delete method for deleting inventory by id.
     *
     * @param id inventory id for deleting.
     * @return message with id of deleted inventory.
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(@PathVariable(name = "id") Long id){
        inventoryService.deleteInventory(id);
        return String.format("Inventory with id: %d was deleted", id);
    }
}
