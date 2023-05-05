package com.dst.inventoryservice.models.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record AddInventoryProductDTO(
        @Positive
        @NotNull
        Long productId,
        @Positive
        @NotNull
        Long supplierId,
        @Positive
        @NotNull
        Integer orderQuantity,
        @Positive
        @NotNull
        BigDecimal batchPrice,
        @Future
        @NotNull
        LocalDate expiryDate
) {
}
