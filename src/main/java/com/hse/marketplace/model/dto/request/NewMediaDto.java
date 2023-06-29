package com.hse.marketplace.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewMediaDto {
    @NotNull
    @Min(1)
    private Long reviewId;
    @NotEmpty
    private String mediaUrl;
}
