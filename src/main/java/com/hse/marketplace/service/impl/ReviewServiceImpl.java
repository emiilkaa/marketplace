package com.hse.marketplace.service.impl;

import com.hse.marketplace.model.dao.entity.Media;
import com.hse.marketplace.model.dao.entity.Review;
import com.hse.marketplace.model.dao.repository.MediaRepository;
import com.hse.marketplace.model.dao.repository.ReviewRepository;
import com.hse.marketplace.model.dto.request.NewMediaDto;
import com.hse.marketplace.model.dto.request.ReviewRequestDto;
import com.hse.marketplace.model.dto.response.ReviewResponseDto;
import com.hse.marketplace.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MediaRepository mediaRepository;

    @Override
    public ReviewResponseDto addReview(Long productId, ReviewRequestDto reviewDto) {
        try {
            if (reviewDto.getReviewId() != null) {
                return buildFailResponse("You can't set id for a new review!");
            }
            Review newReview = Review.builder()
                    .productId(productId)
                    .rating(reviewDto.getRating())
                    .message(reviewDto.getMessage())
                    .build();
            Review savedReview = reviewRepository.save(newReview);
            if (reviewDto.getMediaFiles() != null) {
                reviewDto.getMediaFiles().forEach(file -> addMediaToReview(
                        new NewMediaDto(savedReview.getId(), file)
                ));
            }
            return buildSuccessResponse(savedReview);
        } catch (Exception e) {
            return buildFailResponse(e.getMessage());
        }
    }

    @Override
    public ReviewResponseDto changeReview(ReviewRequestDto reviewDto) {
        try {
            if (reviewDto.getReviewId() == null) {
                return buildFailResponse("Pass reviewId to change the review");
            }
            Optional<Review> reviewOptional = reviewRepository.findById(reviewDto.getReviewId());
            if (reviewOptional.isEmpty()) {
                return buildFailResponse(String.format("Review with id %d not found", reviewDto.getReviewId()));
            }
            reviewOptional.ifPresent(r -> {
                r.setRating(reviewDto.getRating());
                r.setMessage(reviewDto.getMessage() == null ? r.getMessage() : reviewDto.getMessage());
            });
            if (reviewDto.getMediaFiles() != null) {
                reviewDto.getMediaFiles().forEach(file -> addMediaToReview(
                        new NewMediaDto(reviewDto.getReviewId(), file)
                ));
            }
            Review newReview = reviewRepository.save(reviewOptional.get());
            return buildSuccessResponse(newReview);
        } catch (Exception e) {
            return buildFailResponse(e.getMessage());
        }
    }

    @Override
    public ReviewResponseDto addMediaToReview(NewMediaDto mediaDto) {
        try {
            Optional<Review> reviewOptional = reviewRepository.findById(mediaDto.getReviewId());
            if (reviewOptional.isEmpty()) {
                return buildFailResponse(String.format("Review with id %d not found", mediaDto.getReviewId()));
            }

            Long reviewId = mediaDto.getReviewId();
            String url = mediaDto.getMediaUrl();
            List<Media> foundFiles = mediaRepository.findAllByReviewIdAndUrl(reviewId, url);
            if (foundFiles.isEmpty()) {
                Media newMedia = Media.builder()
                        .reviewId(reviewId)
                        .url(url)
                        .deleted(false)
                        .build();
                mediaRepository.save(newMedia);
            } else if (foundFiles.get(0).getDeleted()) {
                restoreDeletedMediaFile(foundFiles.get(0));
            }
            return buildSuccessResponse(reviewOptional.get());
        } catch (Exception e) {
            return buildFailResponse(e.getMessage());
        }
    }

    @Override
    public Boolean deleteMediaFile(NewMediaDto mediaDto) {
        try {
            List<Media> media = mediaRepository.findAllByReviewIdAndUrl(mediaDto.getReviewId(), mediaDto.getMediaUrl());
            if (media.isEmpty()) {
                return false;
            }
            media.forEach(m -> m.setDeleted(true));
            mediaRepository.saveAll(media);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void restoreDeletedMediaFile(Media deletedMedia) {
        deletedMedia.setDeleted(false);
        mediaRepository.save(deletedMedia);
    }

    private ReviewResponseDto buildFailResponse(String message) {
        return ReviewResponseDto.builder()
                .success(false)
                .message(message)
                .build();
    }

    private ReviewResponseDto buildSuccessResponse(Review review) {
        return ReviewResponseDto.builder()
                .review(review.toReviewDto())
                .build();
    }
}
