package com.hse.marketplace.controller;

import com.hse.marketplace.model.dto.request.NewMediaDto;
import com.hse.marketplace.model.dto.request.ReviewRequestDto;
import com.hse.marketplace.model.dto.response.ReviewResponseDto;
import com.hse.marketplace.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping(
            value = "/{product_id}",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<ReviewResponseDto> addNewReview(
            @PathVariable(value = "product_id") Long productId,
            @RequestBody @NotNull @Valid ReviewRequestDto request
    ) {
        return ResponseEntity.ok(reviewService.addReview(productId, request));
    }

    @PatchMapping(
            value = "",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<ReviewResponseDto> updateReview(@RequestBody @NotNull @Valid ReviewRequestDto request) {
        return ResponseEntity.ok(reviewService.changeReview(request));
    }

    @PostMapping(
            value = "/add_media",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<ReviewResponseDto> addMedia(@RequestBody @NotNull @Valid NewMediaDto request) {
        return ResponseEntity.ok(reviewService.addMediaToReview(request));
    }

    @DeleteMapping(
            value = "/delete_media",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<Map<String, Boolean>> deleteMedia(@RequestBody @NotNull @Valid NewMediaDto request) {
        Map<String, Boolean> result = new HashMap<>();
        result.put("success", reviewService.deleteMediaFile(request));
        return ResponseEntity.ok(result);
    }
}
