package com.dst.inventoryservice.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RecipeDTO(
        Long id,
        @Size(min = 3, max = 40)
        @NotNull
        String name,
        @Pattern(regexp = "^APPETIZER$|^SALAD$|^MAIN$|^DESSERT$|^COCKTAIL$|^HOT_DRINK$|^COLD_DRINK$")
        @NotNull
        String category
) {
}
