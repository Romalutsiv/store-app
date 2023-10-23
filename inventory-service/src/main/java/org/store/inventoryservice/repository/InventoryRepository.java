package org.store.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.store.inventoryservice.model.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory ,Long> {

    /**
     * @param skuCode you want to find
     * @return Optional with Inventory model you trying to find.
     */
    Optional<Inventory> findBySkuCode(String skuCode);

    /**
     * @param skuCodes list of scuCodes.
     * @return list of Inventory models with present scuCodes.
     */
    List<Inventory> findBySkuCodeIn(List<String> skuCodes);
}
