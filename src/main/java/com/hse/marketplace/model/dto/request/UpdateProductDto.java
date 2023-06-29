package com.hse.marketplace.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDto {

    @NotNull
    @Min(1)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer availableQuantity;
}
