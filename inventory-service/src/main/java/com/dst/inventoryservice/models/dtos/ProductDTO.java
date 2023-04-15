package com.dst.inventoryservice.models.dtos;

import com.dst.inventoryservice.models.enums.ProductCategory;
import com.dst.inventoryservice.models.enums.UnitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProductDTO {
    private final String name;
    private final ProductCategory category;
    private final UnitType unit;
}