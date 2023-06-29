package com.hse.marketplace.model.dto.response;

import com.hse.marketplace.model.dto.ReviewDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    @Builder.Default
    private boolean success = true;
    @Builder.Default
    private String message = "";
    private String name;
    private String description;
    private BigDecimal price;
    private Integer availableQuantity;
    private BigDecimal averageRating;
    @Builder.Default
    private List<ReviewDto> reviews = new ArrayList<>();
}
