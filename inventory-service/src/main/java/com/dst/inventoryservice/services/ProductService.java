package com.dst.inventoryservice.services;

import com.dst.inventoryservice.models.Product;
import com.dst.inventoryservice.models.dtos.ProductDTO;
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
                .unit(newProduct.unit())
                .category(newProduct.category())
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
        return ProductDTO.builder()
                .name(product.getName())
                .category(product.getCategory())
                .unit(product.getUnit())
                .build();
    }
}
