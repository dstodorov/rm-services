package com.dst.inventoryservice.models.dtos;

import com.dst.inventoryservice.models.RecipeProduct;
import lombok.Builder;

import java.util.List;

@Builder
public record RecipeProductsDTO(List<RecipeProductDTO> products) {
}
