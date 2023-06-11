package com.dst.inventoryservice.controllers;

import com.dst.inventoryservice.models.dtos.RecipeProductsDTO;
import com.dst.inventoryservice.services.RecipeProductsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/recipeProducts")
public class RecipeProductsController {

    private final RecipeProductsService recipeProductsService;

    @PostMapping("/{recipeId}")
    public ResponseEntity<RecipeProductsDTO> addRecipeProducts(@PathVariable Long recipeId, @Valid @RequestBody RecipeProductsDTO recipeProductsDTO, UriComponentsBuilder uriComponentsBuilder) {

        Long recipeIdResponse = this.recipeProductsService.addRecipeProducts(recipeId, recipeProductsDTO);


        if (recipeId == -1L) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.created(uriComponentsBuilder.path("/api/v1/recipes/{id}").build(recipeIdResponse)).build();
    }
}
