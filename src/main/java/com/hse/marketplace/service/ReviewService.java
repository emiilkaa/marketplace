package com.hse.marketplace.service;

import com.hse.marketplace.model.dto.request.NewMediaDto;
import com.hse.marketplace.model.dto.request.ReviewRequestDto;
import com.hse.marketplace.model.dto.response.ReviewResponseDto;

public interface ReviewService {

    ReviewResponseDto addReview(Long productId, ReviewRequestDto reviewDto);
    ReviewResponseDto changeReview(ReviewRequestDto reviewDto);
    ReviewResponseDto addMediaToReview(NewMediaDto mediaDto);
    Boolean deleteMediaFile(NewMediaDto mediaDto);
}
