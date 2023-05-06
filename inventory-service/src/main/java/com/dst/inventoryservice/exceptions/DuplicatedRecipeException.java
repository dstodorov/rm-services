package com.dst.inventoryservice.exceptions;

public class DuplicatedRecipeException extends RuntimeException {
    public DuplicatedRecipeException(String id) {
        super(id);
    }
}
