package com.dst.inventoryservice.services;

import com.dst.inventoryservice.exceptions.DuplicatedProductException;
import com.dst.inventoryservice.exceptions.ProductNotFoundException;
import com.dst.inventoryservice.models.Product;
import com.dst.inventoryservice.models.dtos.ProductDTO;
import com.dst.inventoryservice.models.enums.ProductCategory;
import com.dst.inventoryservice.models.enums.UnitType;
import com.dst.inventoryservice.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
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
    @DisplayName("Get all products - return list of products")
    void getAllProductsList() {

        // Arrange
        Product steakProduct = Product.builder()
                .id(1L)
                .name("Steak")
                .category(ProductCategory.MEAT)
                .unit(UnitType.KG)
                .build();

        Product bananaProduct = Product.builder()
                .id(2L)
                .name("Banana")
                .category(ProductCategory.FRUIT)
                .unit(UnitType.GR)
                .build();

        when(productRepository.count()).thenReturn(2L);
        when(productRepository.findAll()).thenReturn(Arrays.asList(steakProduct, bananaProduct));

        // Act
        Optional<List<ProductDTO>> result = productService.getAll();

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(2, result.get().size());

        ProductDTO steakProductDTO = result.get().get(0);
        ProductDTO bananaProductDTO = result.get().get(1);

        Assertions.assertEquals(steakProduct.getName(), steakProductDTO.name());
        Assertions.assertEquals(steakProduct.getCategory().name(), steakProductDTO.category());
        Assertions.assertEquals(steakProduct.getUnit().name(), steakProductDTO.unit());

        Assertions.assertEquals(bananaProduct.getName(), bananaProductDTO.name());
        Assertions.assertEquals(bananaProduct.getCategory().name(), bananaProductDTO.category());
        Assertions.assertEquals(bananaProduct.getUnit().name(), bananaProductDTO.unit());
    }

    @Test
    @DisplayName("Get all products - return empty list")
    void getAllProductsEmptyList() {
        // Arrange
        when(productRepository.count()).thenReturn(0L);

        // Act
        Optional<List<ProductDTO>> result = productService.getAll();

        // Assert
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Update product - successful")
    void updateProductSuccessful() {

        // Arrange
        Product steakProduct = Product.builder()
                .id(1L)
                .name("Steak")
                .category(ProductCategory.MEAT)
                .unit(UnitType.KG)
                .build();

        when(this.productRepository.findById(steakProduct.getId())).thenReturn(Optional.of(steakProduct));

        ProductDTO steakProductDTO = ProductDTO
                .builder()
                .name("Steak2")
                .category("MEAT")
                .unit("KG")
                .build();

        Product updatedProduct = Product
                .builder()
                .id(1L)
                .name(steakProductDTO.name())
                .unit(UnitType.valueOf(steakProductDTO.unit()))
                .category(ProductCategory.valueOf(steakProductDTO.category()))
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // Act
        ProductDTO updatedProductDTO = this.productService.updateProduct(steakProduct.getId(), steakProductDTO);


        // Assert
        assertEquals(steakProductDTO.name(), updatedProductDTO.name());
        assertEquals(steakProductDTO.category(), updatedProductDTO.category());
        assertEquals(steakProductDTO.unit(), updatedProductDTO.unit());

        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Update not existing product - throws exception")
    void updateNotExistingProductThrowException() {

        // Arrange
        when(this.productRepository.findById(anyLong())).thenThrow(ProductNotFoundException.class);

        ProductDTO steakProductDTO = ProductDTO
                .builder()
                .name("Steak2")
                .category("MEAT")
                .unit("KG")
                .build();

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            this.productService.updateProduct(1L, steakProductDTO);
        });
    }

    @Test
    @DisplayName("Update product with duplicated name - throws exception")
    void updateProductWithDuplicatedNameThrowException() {

        // Arrange
        Product steakProduct = Product.builder()
                .id(1L)
                .name("Steak")
                .category(ProductCategory.MEAT)
                .unit(UnitType.KG)
                .build();

        Product steakProduct2 = Product.builder()
                .id(2L)
                .name("Steak")
                .category(ProductCategory.MEAT)
                .unit(UnitType.KG)
                .build();

        when(this.productRepository.findById(steakProduct.getId())).thenReturn(Optional.of(steakProduct));

        ProductDTO steakProductDTO = ProductDTO
                .builder()
                .name("Steak2")
                .category("MEAT")
                .unit("KG")
                .build();

        ProductDTO steakProductDTO2 = ProductDTO
                .builder()
                .name("Steak")
                .category("MEAT")
                .unit("KG")
                .build();

        Product updatedProduct = Product
                .builder()
                .id(1L)
                .name(steakProductDTO.name())
                .unit(UnitType.valueOf(steakProductDTO.unit()))
                .category(ProductCategory.valueOf(steakProductDTO.category()))
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // Act
        this.productService.updateProduct(steakProduct.getId(), steakProductDTO);

        when(this.productRepository.findByName("Steak")).thenReturn(Optional.of(steakProduct2));

        // Assert
        assertThrows(DuplicatedProductException.class, () -> {
            this.productService.updateProduct(1L, steakProductDTO2);
        });
    }
}