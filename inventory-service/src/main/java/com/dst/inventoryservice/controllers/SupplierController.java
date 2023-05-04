package com.dst.inventoryservice.controllers;

import com.dst.inventoryservice.models.dtos.SupplierDTO;
import com.dst.inventoryservice.services.SupplierService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierDTO> addSupplier(@RequestBody @Valid SupplierDTO supplierDTO, UriComponentsBuilder uriComponentsBuilder) {
        Long supplierId = this.supplierService.createSupplier(supplierDTO);

        if (supplierId == -1) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.created(uriComponentsBuilder.path("/api/v1/suppliers/{id}").build(supplierId)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable Long id) {
        Optional<SupplierDTO> supplier = this.supplierService.getSupplier(id);

        return supplier.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
