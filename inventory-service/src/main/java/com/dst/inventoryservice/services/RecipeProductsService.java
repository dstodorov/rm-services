package com.dst.inventoryservice.services;

import com.dst.inventoryservice.exceptions.ProductNotFoundException;
import com.dst.inventoryservice.exceptions.RecipeNotFoundException;
import com.dst.inventoryservice.models.Product;
import com.dst.inventoryservice.models.Recipe;
import com.dst.inventoryservice.models.RecipeProduct;
import com.dst.inventoryservice.models.dtos.RecipeProductDTO;
import com.dst.inventoryservice.models.dtos.RecipeProductsDTO;
import com.dst.inventoryservice.repositories.ProductRepository;
import com.dst.inventoryservice.repositories.RecipeProductRepository;
import com.dst.inventoryservice.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RecipeProductsService {

    private final RecipeProductRepository recipeProductRepository;
    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;

    public Long addRecipeProducts(Long recipeId, RecipeProductsDTO productsDTO) {

        List<Long> productsById = productsDTO.products().stream().map(RecipeProductDTO::productId).toList();

        // In case of missing products, throw exception
        if (productsById.size() != productsDTO.products().size()) {
            throw new ProductNotFoundException("To be refactored!");
        }

        Optional<Recipe> recipe = this.recipeRepository.findById(recipeId);

        if (recipe.isEmpty()) {
            throw new RecipeNotFoundException(recipeId.toString());
        }

        List<RecipeProduct> productsList = productsDTO.products()
                .stream()
                .map(p -> mapToRecipeProduct(recipe.get(), p))
                .toList();

        this.recipeProductRepository.saveAll(productsList);

        return recipeId;
    }

    private RecipeProduct mapToRecipeProduct(Recipe recipe, RecipeProductDTO recipeProduct) {
        Optional<Product> productById = this.productRepository.findById(recipeProduct.productId());

        // Not needed check, but made just to remove null waring on productById.get() line below
        // Its guaranteed this product exists in counter check above
        if (productById.isEmpty()) {
            throw new ProductNotFoundException(recipeProduct.productId().toString());
        }

        return RecipeProduct
                .builder()
                .recipe(recipe)
                .product(productById.get())
                .quantity(recipeProduct.quantity())
                .build();
    }
}
