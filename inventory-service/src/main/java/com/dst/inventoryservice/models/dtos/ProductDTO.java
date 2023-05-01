package com.dst.inventoryservice.models.dtos;

import com.dst.inventoryservice.models.enums.ProductCategory;
import com.dst.inventoryservice.models.enums.UnitType;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record ProductDTO(Long id,
                         String name,
                         @Pattern(regexp = "MEAT|SEAFOOD|VEGETABLE|FRUIT|GRAINS|DIARY|NUT_SEED|HERB_SPICE|ALCOHOLIC_DRINK|NON_ALCOHOLIC_DRINK")
                         String category,
                         @Pattern(regexp = "KG|GR")
                         String unit) {
}