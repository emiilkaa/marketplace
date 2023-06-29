package com.hse.marketplace.service;

import com.hse.marketplace.model.dto.request.CreateProductDto;
import com.hse.marketplace.model.dto.request.UpdateProductDto;
import com.hse.marketplace.model.dto.response.NewProductResponseDto;
import com.hse.marketplace.model.dto.response.ProductDto;

public interface ProductService {

    NewProductResponseDto createProduct(CreateProductDto newProduct);
    NewProductResponseDto updateProduct(UpdateProductDto changedProduct);
    Boolean deleteProduct(Long productId);
    ProductDto getProductWithReviews(Long productId);
}
