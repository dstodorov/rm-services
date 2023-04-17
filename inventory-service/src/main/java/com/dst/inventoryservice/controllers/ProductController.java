package com.dst.inventoryservice.controllers;

import com.dst.inventoryservice.exceptions.InvalidProductException;
import com.dst.inventoryservice.models.dtos.ProductDTO;
import com.dst.inventoryservice.services.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        Optional<List<ProductDTO>> allProducts = this.productService.getAll();

        return allProducts.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id) {
        Optional<ProductDTO> product = this.productService.getProduct(id);

        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO product, UriComponentsBuilder uriComponentsBuilder) {

        Long productId = this.productService.createProduct(product);

        if (productId == -1) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.created(uriComponentsBuilder.path("/api/v1/products/{id}").build(productId)).build();

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        if (ex.getAllErrors().get(0).toString().contains("unit")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getFieldValue("unit").toString() + " is not a valid unit type!");
        }

        if (ex.getAllErrors().get(0).toString().contains("category")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getFieldValue("category").toString() + " is not a valid product category!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
