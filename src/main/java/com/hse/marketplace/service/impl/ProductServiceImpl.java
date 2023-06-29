package com.hse.marketplace.service.impl;

import com.hse.marketplace.model.dao.entity.Product;
import com.hse.marketplace.model.dao.repository.ProductRepository;
import com.hse.marketplace.model.dto.request.CreateProductDto;
import com.hse.marketplace.model.dto.request.UpdateProductDto;
import com.hse.marketplace.model.dto.response.NewProductResponseDto;
import com.hse.marketplace.model.dto.response.ProductDto;
import com.hse.marketplace.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public NewProductResponseDto createProduct(CreateProductDto newProduct) {
        try {
            Product product = Product.builder()
                    .name(newProduct.getName())
                    .description(newProduct.getDescription())
                    .price(newProduct.getPrice())
                    .availableQuantity(newProduct.getAvailableQuantity())
                    .build();
            Product savedProduct = productRepository.save(product);
            return buildSuccessResponse(savedProduct);
        } catch (Exception e) {
            return buildFailResponse(e.getMessage());
        }
    }

    @Override
    public NewProductResponseDto updateProduct(UpdateProductDto changedProduct) {
        try {
            Optional<Product> product = productRepository.findById(changedProduct.getId());
            if (product.isEmpty()) {
                return buildFailResponse(String.format("Product with id %d not found", changedProduct.getId()));
            }
            product.ifPresent(p -> {
                p.setName(changedProduct.getName() == null ? p.getName() : changedProduct.getName());
                p.setDescription(changedProduct.getDescription() == null ? p.getDescription() : changedProduct.getDescription());
                p.setPrice(changedProduct.getPrice() == null ? p.getPrice() : changedProduct.getPrice());
                p.setAvailableQuantity(changedProduct.getAvailableQuantity() == null ? p.getAvailableQuantity() : changedProduct.getAvailableQuantity());
            });
            Product savedProduct = productRepository.save(product.get());
            return buildSuccessResponse(savedProduct);
        } catch (Exception e) {
            return buildFailResponse(e.getMessage());
        }
    }

    @Override
    public Boolean deleteProduct(Long productId) {
        try {
            Optional<Product> product = productRepository.findById(productId);
            if (product.isEmpty()) {
                return false;
            }
            productRepository.delete(product.get());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public ProductDto getProductWithReviews(Long productId) {
        try {
            Optional<Product> product = productRepository.findById(productId);
            if (product.isEmpty()) {
                return ProductDto.builder()
                        .success(false)
                        .message(String.format("Product with id %d not found", productId))
                        .build();
            }
            return product.get().toProductDto();
        } catch (Exception e) {
            return ProductDto.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
        }
    }

    private NewProductResponseDto buildSuccessResponse(Product product) {
        return NewProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .availableQuantity(product.getAvailableQuantity())
                .build();
    }

    private NewProductResponseDto buildFailResponse(String message) {
        return NewProductResponseDto.builder()
                .success(false)
                .message(message)
                .build();
    }
}
