package com.dst.inventoryservice.repositories;

import com.dst.inventoryservice.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
