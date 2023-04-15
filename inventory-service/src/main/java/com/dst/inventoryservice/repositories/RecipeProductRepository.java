package com.dst.inventoryservice.repositories;

import com.dst.inventoryservice.models.RecipeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeProductRepository extends JpaRepository<RecipeProduct, Long> {

}
