package com.dst.inventoryservice.exceptions;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(String id) {
        super(id);
    }
}
