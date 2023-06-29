package com.hse.marketplace.controller;

import com.hse.marketplace.model.dto.request.CreateProductDto;
import com.hse.marketplace.model.dto.request.UpdateProductDto;
import com.hse.marketplace.model.dto.response.NewProductResponseDto;
import com.hse.marketplace.model.dto.response.ProductDto;
import com.hse.marketplace.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping(
            value = "",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<NewProductResponseDto> addNewProduct(@RequestBody @NotNull @Valid CreateProductDto request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PatchMapping(
            value = "",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<NewProductResponseDto> changeProduct(@RequestBody @NotNull @Valid UpdateProductDto request) {
        return ResponseEntity.ok(productService.updateProduct(request));
    }

    @GetMapping(
            value = "/{product_id}",
            produces = {"application/json"}
    )
    public ResponseEntity<ProductDto> getProductInfo(@PathVariable(value = "product_id") Long productId) {
        return ResponseEntity.ok(productService.getProductWithReviews(productId));
    }

    @DeleteMapping(
            value = "/{product_id}",
            produces = {"application/json"}
    )
    public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable(value = "product_id") Long productId) {
        Map<String, Boolean> result = new HashMap<>();
        result.put("success", productService.deleteProduct(productId));
        return ResponseEntity.ok(result);
    }
}
