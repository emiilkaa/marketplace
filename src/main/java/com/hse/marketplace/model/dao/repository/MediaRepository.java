package com.hse.marketplace.model.dao.repository;

import com.hse.marketplace.model.dao.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findAllByReviewIdAndUrl(Long reviewId, String url);
}
