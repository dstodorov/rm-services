package com.dst.inventoryservice.models.dtos;

import lombok.Builder;

@Builder
public record SupplierInfoDTO(Long id, String name, String email, String phoneNumber) {
}
