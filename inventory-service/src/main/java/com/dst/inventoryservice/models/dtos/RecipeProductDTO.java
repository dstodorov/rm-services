package com.dst.inventoryservice.models.dtos;

import lombok.Builder;

@Builder
public record RecipeProductDTO(Long productId, Double quantity) {
}
