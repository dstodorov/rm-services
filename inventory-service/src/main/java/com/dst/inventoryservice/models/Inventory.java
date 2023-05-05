package com.dst.inventoryservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "current_quantity")
    private Double currentQuantity;

    @Column(nullable = false, name = "order_quantity")
    private Double orderQuantity;

    @Column(nullable = false, name = "batch_price")
    private BigDecimal batchPrice;

    @Column(nullable = false, name = "order_date")
    private LocalDate orderDate;

    @Column(nullable = false, name = "expiry_date")
    private LocalDate expiryDate;

    @Basic
    private Boolean wasted;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}
