package com.dst.inventoryservice.exceptions;

public class DuplicatedSupplierException extends RuntimeException {
    public DuplicatedSupplierException(String id) {
        super(id);
    }
}
