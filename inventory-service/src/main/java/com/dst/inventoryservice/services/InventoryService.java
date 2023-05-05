package com.dst.inventoryservice.services;

import com.dst.inventoryservice.exceptions.InventoryProductNotFoundException;
import com.dst.inventoryservice.exceptions.ProductNotFoundException;
import com.dst.inventoryservice.exceptions.SupplierNotFoundException;
import com.dst.inventoryservice.models.Inventory;
import com.dst.inventoryservice.models.Product;
import com.dst.inventoryservice.models.Supplier;
import com.dst.inventoryservice.models.dtos.AddInventoryProductDTO;
import com.dst.inventoryservice.models.dtos.InventoryProductInfoDTO;
import com.dst.inventoryservice.models.dtos.ProductDTO;
import com.dst.inventoryservice.models.dtos.SupplierInfoDTO;
import com.dst.inventoryservice.repositories.InventoryRepository;
import com.dst.inventoryservice.repositories.ProductRepository;
import com.dst.inventoryservice.repositories.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    public Long addToInventory(AddInventoryProductDTO inventoryProductDTO) {
        Supplier supplier = this.supplierRepository
                .findById(inventoryProductDTO.supplierId())
                .orElseThrow(() -> new SupplierNotFoundException(inventoryProductDTO.supplierId().toString()));

        Product product = productRepository
                .findById(inventoryProductDTO.productId())
                .orElseThrow(() -> new ProductNotFoundException(inventoryProductDTO.productId().toString()));

        LocalDate orderDate = LocalDate.now();

        Inventory inventoryProduct = Inventory
                .builder()
                .batchPrice(inventoryProductDTO.batchPrice())
                .orderDate(orderDate)
                .orderQuantity(inventoryProductDTO.orderQuantity())
                .currentQuantity(inventoryProductDTO.orderQuantity())
                .expiryDate(inventoryProductDTO.expiryDate())
                .wasted(false)
                .product(product)
                .supplier(supplier)
                .build();

        return this.inventoryRepository.save(inventoryProduct).getId();
    }

    public InventoryProductInfoDTO getInventoryProductInfo(Long id) {
        return this.inventoryRepository
                .findById(id)
                .map(this::map)
                .orElseThrow(() -> new InventoryProductNotFoundException(id.toString()));
    }

    private InventoryProductInfoDTO map(Inventory inventoryProduct) {
        SupplierInfoDTO supplierInfoDTO = SupplierInfoDTO
                .builder()
                .id(inventoryProduct.getSupplier().getId())
                .name(inventoryProduct.getSupplier().getName())
                .email(inventoryProduct.getSupplier().getEmail())
                .phoneNumber(inventoryProduct.getSupplier().getPhoneNumber())
                .build();

        ProductDTO productDTO = ProductDTO
                .builder()
                .id(inventoryProduct.getProduct().getId())
                .name(inventoryProduct.getProduct().getName())
                .category(inventoryProduct.getProduct().getCategory().name())
                .unit(inventoryProduct.getProduct().getUnit().name())
                .build();

        return InventoryProductInfoDTO
                .builder()
                .id(inventoryProduct.getId())
                .batchPrice(inventoryProduct.getBatchPrice())
                .currentQuantity(inventoryProduct.getCurrentQuantity())
                .expiryDate(inventoryProduct.getExpiryDate())
                .wasted(inventoryProduct.getWasted())
                .product(productDTO)
                .supplier(supplierInfoDTO)
                .build();
    }
}
