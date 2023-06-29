package com.hse.marketplace.model.dto.response;

import com.hse.marketplace.model.dto.ReviewDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {
    @Builder.Default
    private boolean success = true;
    @Builder.Default
    private String message = "";

    private ReviewDto review;
}
