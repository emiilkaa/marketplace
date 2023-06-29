package com.hse.marketplace.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long id;
    private Date created;
    private Date updated;
    private String message = "";
    private Integer rating;
    @Builder.Default
    private List<String> mediaFiles = new ArrayList<>();
}
