package com.dst.inventoryservice.controllers;

import com.dst.inventoryservice.models.dtos.ProductDTO;
import com.dst.inventoryservice.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;


    @PostMapping(value = "/add")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product, UriComponentsBuilder uriComponentsBuilder) {

        Long productId = this.productService.createProduct(product);

        return ResponseEntity.created(uriComponentsBuilder.path("/api/v1/products/{id}").build(productId)).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id) {
        Optional<ProductDTO> product = this.productService.getProduct(id);

        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
