package com.dst.inventoryservice.repositories;

import com.dst.inventoryservice.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
