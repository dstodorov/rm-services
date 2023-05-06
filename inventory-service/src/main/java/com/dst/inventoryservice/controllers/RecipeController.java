package com.dst.inventoryservice.controllers;

import com.dst.inventoryservice.models.ErrorMessage;
import com.dst.inventoryservice.models.dtos.RecipeDTO;
import com.dst.inventoryservice.services.RecipeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<RecipeDTO> createRecipe(@Valid @RequestBody RecipeDTO recipeDTO, UriComponentsBuilder uriComponentsBuilder) {

        Long recipeId = this.recipeService.createRecipe(recipeDTO);

        if (recipeId == -1L) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.created(uriComponentsBuilder.path("/api/v1/recipes/{id}").build(recipeId)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable Long id) {

        Optional<RecipeDTO> recipe = this.recipeService.getRecipe(id);

        return recipe
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build());
    }

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        Optional<List<RecipeDTO>> allRecipes = this.recipeService.getAllRecipes();

        return allRecipes.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody RecipeDTO recipeDTO) {
        RecipeDTO recipe = this.recipeService.updateRecipe(id, recipeDTO);

        return ResponseEntity.ok(recipe);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<String> handleValidationExceptions(BindingResult bindingResult) {

        List<String> errors = new ArrayList<>();

        bindingResult.getAllErrors().forEach(e -> errors.add(ErrorMessage
                .builder()
                .field(((FieldError) e).getField())
                .message(e.getDefaultMessage())
                .build().toString()));

        String responseBody = String.format("{\"errors\" : [%s]}", String.join(", ", errors));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(responseBody);
    }
}
