package com.dst.inventoryservice.services;

import com.dst.inventoryservice.models.Product;
import com.dst.inventoryservice.models.dtos.ProductDTO;
import com.dst.inventoryservice.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Long createProduct(ProductDTO newProduct) {

        Product product = Product.builder()
                .name(newProduct.getName())
                .unit(newProduct.getUnit())
                .category(newProduct.getCategory())
                .build();


        return this.productRepository.save(product).getId();
    }

    public Optional<ProductDTO> getProduct(Long id) {
        return this.productRepository.findById(id).map(this::mapToProductDTO);
    }

    private ProductDTO mapToProductDTO(Product product) {
        return ProductDTO.builder()
                .name(product.getName())
                .category(product.getCategory())
                .unit(product.getUnit())
                .build();
    }
}
