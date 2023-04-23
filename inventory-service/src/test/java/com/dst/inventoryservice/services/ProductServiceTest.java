package com.dst.inventoryservice.services;

import com.dst.inventoryservice.models.Product;
import com.dst.inventoryservice.models.dtos.ProductDTO;
import com.dst.inventoryservice.models.enums.ProductCategory;
import com.dst.inventoryservice.models.enums.UnitType;
import com.dst.inventoryservice.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    @DisplayName("Create product - successful")
    void CreateProductSuccessful() {

        // Arrange
        ProductDTO newProduct = ProductDTO
                .builder()
                .name("Broccoli")
                .unit("KG")
                .category("VEGETABLE")
                .build();

        Product product = Product
                .builder()
                .id(1L)
                .name("Broccoli")
                .unit(UnitType.KG)
                .category(ProductCategory.VEGETABLE)
                .build();

        when(productRepository.findByName(newProduct.name())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Long productId = this.productService.createProduct(newProduct);

        // Assert
        assertNotNull(productId);
        assertEquals(product.getId(), productId);
        verify(productRepository, times(1)).findByName(newProduct.name());
        verify(productRepository, times(1)).save(any(Product.class));

        this.productRepository.deleteAll();
    }

    @Test
    @DisplayName("Create product with same name - not successful")
    void CreateSameProductFailed() {

        // Arrange
        ProductDTO existingProduct = ProductDTO
                .builder()
                .name("Broccoli")
                .unit("KG")
                .category("VEGETABLE")
                .build();

        Product product = Product
                .builder()
                .id(1L)
                .name(existingProduct.name())
                .unit(UnitType.valueOf(existingProduct.unit()))
                .category(ProductCategory.valueOf(existingProduct.category()))
                .build();

        when(productRepository.findByName(existingProduct.name())).thenReturn(Optional.of(product));

        ProductDTO newProduct = ProductDTO
                .builder()
                .name("Broccoli")
                .unit("KG")
                .category("VEGETABLE")
                .build();

        // Act
        Long productId = this.productService.createProduct(newProduct);

        // Assert

        assertNotNull(productId);
        assertEquals(-1L, productId);
        verify(productRepository, times(1)).findByName(newProduct.name());
        verify(productRepository, times(0)).save(any(Product.class));

        this.productRepository.deleteAll();
    }

    @Test
    @DisplayName("Get product by ID - Return product")
    void getProductByIdReturnProduct() {

        // Arrange
        ProductDTO newProduct = ProductDTO
                .builder()
                .name("Broccoli")
                .unit("KG")
                .category("VEGETABLE")
                .build();

        Product product = Product
                .builder()
                .id(1L)
                .name("Broccoli")
                .unit(UnitType.KG)
                .category(ProductCategory.VEGETABLE)
                .build();

        when(this.productRepository.save(any(Product.class))).thenReturn(product);
        when(this.productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        // Act
        Long productId = this.productService.createProduct(newProduct);

        // Assert
        Optional<ProductDTO> optProductDTO = this.productService.getProduct(productId);
        assertTrue(optProductDTO.isPresent());
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productRepository, times(1)).findById(productId);

        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Get product by ID - Return empty list")
    void getProductByIdReturnEmptyList() {

    }

}