package org.store.inventoryservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.store.inventoryservice.dto.InventoryInStockResponse;
import org.store.inventoryservice.dto.InventoryRequest;
import org.store.inventoryservice.model.Inventory;
import org.store.inventoryservice.repository.InventoryRepository;
import org.store.inventoryservice.service.InventoryService;

import java.util.List;
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

    @Override
    public InventoryInStockResponse isInStoke(@NonNull final String skuCode) {
        Inventory inventoryForChecking = getByScuCode(skuCode);
        if (inventoryForChecking != null){
            if (inventoryForChecking.getQuantity() > 0) return new InventoryInStockResponse(skuCode, true);
            else return new InventoryInStockResponse(skuCode, false);
        } else {
            throw new RuntimeException("Not found");
        }
    }

    @Override
    public List<InventoryInStockResponse> isInStokeList(@NonNull final List<String> skuCodes) {
        return skuCodes.stream().map(this::isInStoke).toList();
    }

    @Override
    public Inventory getByScuCode(@NonNull final String skuCode) {
        return repository.findBySkuCode(skuCode).orElseThrow(() -> new EntityNotFoundException("Product with skuCode: " + skuCode + " not found!"));
    }

    @Override
    public Inventory create(@NonNull final InventoryRequest inventoryRequest) {
        Inventory inventory = new Inventory();
        inventory.setSkuCode(inventoryRequest.scuCode());
        inventory.setQuantity(inventoryRequest.quantity());
        return repository.save(inventory);
    }

    @Override
    public Inventory reduceQuantity(@NonNull final InventoryRequest inventoryRequest) {
        Inventory inventory = getByScuCode(inventoryRequest.scuCode());
        inventory.setQuantity(inventory.getQuantity() - inventoryRequest.quantity());
        return repository.save(inventory);
    }

    @Override
    public Inventory increaseQuantity(@NonNull final InventoryRequest inventoryRequest) {
        Inventory inventory = getByScuCode(inventoryRequest.scuCode());
        inventory.setQuantity(inventory.getQuantity() + inventoryRequest.quantity());
        return repository.save(inventory);
    }

    @Override
    public void deleteInventory(@NonNull final Long id) {
        repository.deleteById(id);
    }

    @Override
    public String createListOfInventory(@NonNull final List<InventoryRequest> requestList, @NonNull final String invoiceType) {
        int countOfNewInventory = 0;
        int countOfExistInventory = 0;
        switch (invoiceType){
            case "INCOME" -> {
                for (InventoryRequest req : requestList) {
                    Inventory byScuCode = getByScuCode(req.scuCode());
                    if (byScuCode == null){
                        create(req);
                        countOfNewInventory++;
                    } else {
                        increaseQuantity(req);
                        countOfExistInventory++;
                    }
                }
            }
            case "OUTCOME" -> {
                for (InventoryRequest req : requestList) {
                    Inventory byScuCode = getByScuCode(req.scuCode());
                    if (byScuCode == null){
                        create(req);
                        countOfNewInventory++;
                    } else {
                        reduceQuantity(req);
                        countOfExistInventory++;
                    }
                }

            }
        }
        return String.format("%d inventory was created, %d inventory was updated!", countOfNewInventory, countOfExistInventory);
    }
}
