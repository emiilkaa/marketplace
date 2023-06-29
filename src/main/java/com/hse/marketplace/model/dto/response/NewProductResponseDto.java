package com.hse.marketplace.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewProductResponseDto {
    @Builder.Default
    private boolean success = true;
    @Builder.Default
    private String message = "";

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer availableQuantity;
}
