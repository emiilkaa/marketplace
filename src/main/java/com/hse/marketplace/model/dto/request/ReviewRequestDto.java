package com.hse.marketplace.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {

    private Long reviewId;
    private String message;
    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;
    private List<String> mediaFiles;
}
