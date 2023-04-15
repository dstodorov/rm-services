package com.dst.inventoryservice.repositories;

import com.dst.inventoryservice.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
