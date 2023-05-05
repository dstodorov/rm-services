package com.dst.inventoryservice.models.dtos;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record InventoryProductInfoDTO(
        Long id,
        Double currentQuantity,
        SupplierInfoDTO supplier,
        ProductDTO product,
        BigDecimal batchPrice,
        LocalDate expiryDate,
        Boolean wasted
) {
}
