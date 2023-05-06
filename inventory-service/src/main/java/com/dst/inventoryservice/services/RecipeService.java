package com.dst.inventoryservice.services;

import com.dst.inventoryservice.exceptions.DuplicatedRecipeException;
import com.dst.inventoryservice.exceptions.RecipeNotFoundException;
import com.dst.inventoryservice.models.Recipe;
import com.dst.inventoryservice.models.dtos.RecipeDTO;
import com.dst.inventoryservice.models.enums.RecipeCategory;
import com.dst.inventoryservice.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public Long createRecipe(RecipeDTO recipeDTO) {
        Optional<Recipe> recipeByName = this.recipeRepository.findByName(recipeDTO.name());

        if (recipeByName.isPresent()) {
            return -1L;
        }

        Recipe recipe = Recipe
                .builder()
                .name(recipeDTO.name())
                .category(RecipeCategory.valueOf(recipeDTO.category()))
                .build();

        return this.recipeRepository.save(recipe).getId();
    }


    public RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO) {

        // Product not found, throw exception
        Recipe recipeById = this.recipeRepository
                .findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id.toString()));

        // Duplicated recipe name, throw exception
        if (this.recipeRepository
                .findByName(recipeDTO.name())
                .filter(recipe -> !recipe.getId().equals(id)).isPresent()) {
            throw new DuplicatedRecipeException(id.toString());
        }

        // Saving changes
        recipeById.setName(recipeDTO.name());
        recipeById.setCategory(RecipeCategory.valueOf(recipeDTO.category()));

        this.recipeRepository.save(recipeById);

        return RecipeDTO
                .builder()
                .id(id)
                .name(recipeDTO.name())
                .category(recipeDTO.category())
                .build();
    }

    public Optional<RecipeDTO> getRecipe(Long id) {
        return this.recipeRepository.findById(id).map(this::mapToRecipeDTO);
    }

    public Optional<List<RecipeDTO>> getAllRecipes() {
        if (this.recipeRepository.count() == 0) {
            return Optional.empty();
        }

        return Optional.of(this.recipeRepository
                .findAll()
                .stream()
                .map(this::mapToRecipeDTO)
                .toList());
    }

    private RecipeDTO mapToRecipeDTO(Recipe recipe) {
        return RecipeDTO
                .builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .category(recipe.getCategory().name())
                .build();
    }
}
