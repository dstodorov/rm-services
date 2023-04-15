package com.dst.inventoryservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, name = "current_quantity")
    private Integer currentQuantity;

    @Column(nullable = false, name = "order_quantity")
    private Integer orderQuantity;

    @Column(nullable = false, name = "batch_price")
    private BigDecimal batchPrice;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}
