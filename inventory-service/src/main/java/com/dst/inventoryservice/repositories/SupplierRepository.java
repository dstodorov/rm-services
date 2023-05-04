package com.dst.inventoryservice.repositories;

import com.dst.inventoryservice.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByEmail(String email);

    Optional<Supplier> findByPhoneNumber(String phoneNumber);

    Optional<Supplier> findByName(String name);
}
