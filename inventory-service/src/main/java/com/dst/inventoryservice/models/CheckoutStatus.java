package com.dst.inventoryservice.models;

import lombok.Builder;

import java.util.List;

@Builder
public record CheckoutStatus(Boolean successfulCheckout, String statusMessage, List<String> errors) {
}
