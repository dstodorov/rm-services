package com.dst.inventoryservice.services;

import com.dst.inventoryservice.exceptions.DuplicatedProductException;
import com.dst.inventoryservice.exceptions.ProductNotFoundException;
import com.dst.inventoryservice.models.Product;
import com.dst.inventoryservice.models.dtos.ProductDTO;
import com.dst.inventoryservice.models.enums.ProductCategory;
import com.dst.inventoryservice.models.enums.UnitType;
import com.dst.inventoryservice.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Long createProduct(ProductDTO newProduct) {

        Optional<Product> productByName = this.productRepository.findByName(newProduct.name());

        if (productByName.isPresent()) {
            return -1L;
        }

        Product product = Product.builder()
                .name(newProduct.name())
                .unit(UnitType.valueOf(newProduct.unit()))
                .category(ProductCategory.valueOf(newProduct.category()))
                .build();


        return this.productRepository.save(product).getId();
    }

    public Optional<ProductDTO> getProduct(Long id) {
        return this.productRepository.findById(id).map(this::mapToProductDTO);
    }

    public Optional<List<ProductDTO>> getAll() {
        if (this.productRepository.count() == 0) {
            return Optional.empty();
        }

        return Optional.of(this.productRepository.findAll().stream().map(this::mapToProductDTO).toList());
    }

    private ProductDTO mapToProductDTO(Product product) {
        return ProductDTO
                .builder()
                .name(product.getName())
                .category(product.getCategory().name())
                .unit(product.getUnit().name())
                .build();
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        // Check if product is found, if not throw exception
        this.productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id.toString()));

        // Check if product with this name exists in database
        if (productRepository.findByName(productDTO.name()).isPresent()) {
            throw new DuplicatedProductException(id.toString());
        }

        // Saving changes
        Product savedProduct = this.productRepository.save(
                Product
                        .builder()
                        .id(id).name(productDTO.name())
                        .category(ProductCategory.valueOf(productDTO.category()))
                        .unit(UnitType.valueOf(productDTO.unit()))
                        .build()
        );

        return ProductDTO
                .builder()
                .name(savedProduct.getName())
                .category(savedProduct.getCategory().name())
                .unit(savedProduct.getUnit().name())
                .build();

    }
}
