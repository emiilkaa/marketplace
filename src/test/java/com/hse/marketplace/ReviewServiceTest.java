package com.hse.marketplace;

import com.hse.marketplace.model.dao.entity.Product;
import com.hse.marketplace.model.dao.entity.Review;
import com.hse.marketplace.model.dao.repository.ProductRepository;
import com.hse.marketplace.model.dao.repository.ReviewRepository;
import com.hse.marketplace.model.dto.request.NewMediaDto;
import com.hse.marketplace.model.dto.request.ReviewRequestDto;
import com.hse.marketplace.model.dto.response.ReviewResponseDto;
import com.hse.marketplace.service.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReviewServiceTest extends BaseTest {

    private final ReviewService reviewService;

    private final ReviewRepository reviewRepository;

    private final ProductRepository productRepository;

    @Autowired
    ReviewServiceTest(MockMvc mockMvc,
                      ReviewService reviewService,
                      ReviewRepository reviewRepository,
                      ProductRepository productRepository) {
        super(mockMvc);
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    @Test
    @DisplayName("Добавление нового отзыва")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/add_product.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clear.sql")
    })
    public void addReviewTest() {
        Optional<Product> product = productRepository.findById(1L);
        assertTrue(product.isPresent());
        assertEquals(0, product.get().getReviews().size());

        ReviewRequestDto request = ReviewRequestDto.builder()
                .rating(5)
                .message("Отличный товар!")
                .build();
        ReviewResponseDto response = reviewService.addReview(1L, request);
        assertTrue(response.isSuccess());
        assertEquals(5, response.getReview().getRating());
        assertEquals(0, response.getReview().getMediaFiles().size());

        product = productRepository.findById(1L);
        assertTrue(product.isPresent());
        assertEquals(1, product.get().getReviews().size());
        assertEquals(5, product.get().getReviews().get(0).getRating());
    }

    @Test
    @DisplayName("Исправление отзыва")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/add_products_with_reviews.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clear.sql")
    })
    public void changeReviewTest() {
        Optional<Review> review = reviewRepository.findById(1L);
        assertTrue(review.isPresent());
        assertEquals(5, review.get().getRating());

        ReviewRequestDto request = ReviewRequestDto.builder()
                .reviewId(1L)
                .rating(4)
                .build();
        ReviewResponseDto response = reviewService.changeReview(request);
        assertTrue(response.isSuccess());
        assertEquals(4, response.getReview().getRating());

        review = reviewRepository.findById(1L);
        assertTrue(review.isPresent());
        assertEquals(4, review.get().getRating());

        Optional<Product> product = productRepository.findById(2L);
        assertTrue(product.isPresent());
        assertEquals(0, product.get().toProductDto().getAverageRating().compareTo(new BigDecimal(4)));
    }

    @Test
    @DisplayName("Добавление нового медиафайла")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/add_products_with_reviews.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clear.sql")
    })
    public void addMediaTest() {
        Optional<Review> review = reviewRepository.findById(1L);
        assertTrue(review.isPresent());
        assertEquals(0, review.get().getMediaFiles().size());

        NewMediaDto request = NewMediaDto.builder()
                .reviewId(1L)
                .mediaUrl("https://irecommend.ru/sites/default/files/imagecache/copyright1/user-images/1349391/QJTeXPqjSCWJQCARCzciig.jpeg")
                .build();
        ReviewResponseDto response = reviewService.addMediaToReview(request);
        assertTrue(response.isSuccess());

        review = reviewRepository.findById(1L);
        assertTrue(review.isPresent());
        assertEquals(1, review.get().getMediaFiles().size());
        assertEquals(
                "https://irecommend.ru/sites/default/files/imagecache/copyright1/user-images/1349391/QJTeXPqjSCWJQCARCzciig.jpeg",
                review.get().getMediaFiles().get(0).getUrl()
        );
    }
}
