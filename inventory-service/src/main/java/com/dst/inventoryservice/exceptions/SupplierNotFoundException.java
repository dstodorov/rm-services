package com.dst.inventoryservice.exceptions;

public class SupplierNotFoundException extends RuntimeException {
    public SupplierNotFoundException(String id) {
        super(id);
    }
}
