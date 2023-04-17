package com.dst.inventoryservice.models.dtos;

import com.dst.inventoryservice.models.enums.ProductCategory;
import com.dst.inventoryservice.models.enums.UnitType;
import lombok.Builder;
@Builder
public record ProductDTO(String name, ProductCategory category, UnitType unit) {
}