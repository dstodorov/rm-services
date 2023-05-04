package com.dst.inventoryservice.models;

import lombok.Builder;

@Builder
public class ErrorMessage {
    private String field;
    private String message;

    @Override
    public String toString() {
        return "{\"field\":\"" + this.field + "\",\"message\":" + "\"" + this.message + "\"}";
    }
}
