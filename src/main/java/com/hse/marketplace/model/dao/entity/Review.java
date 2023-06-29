package com.hse.marketplace.model.dao.entity;

import com.hse.marketplace.model.dto.ReviewDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
@EqualsAndHashCode(of = "id")
public class Review implements Serializable {

    @Id
    @Column(updatable = false, name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviewSequence")
    @SequenceGenerator(name = "reviewSequence", sequenceName = "seq_review", allocationSize = 1)
    private Long id;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "date_updated")
    private Date dateUpdated;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "post_message")
    private String message;

    @Column(name = "rating")
    private Integer rating;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "review_id")
    @Where(clause = "deleted = false")
    private List<Media> mediaFiles;

    @PrePersist
    private void prePersist() {
        Date now = new Date();
        dateCreated = now;
        dateUpdated = now;
    }

    @PreUpdate
    private void preUpdate() {
        dateUpdated = new Date();
    }

    public ReviewDto toReviewDto() {
        List<String> urls = mediaFiles == null ? new ArrayList<>() :
                mediaFiles.stream().map(Media::getUrl).collect(Collectors.toList());
        return ReviewDto.builder()
                .id(id)
                .created(dateCreated)
                .updated(dateUpdated)
                .message(message)
                .rating(rating)
                .mediaFiles(urls)
                .build();
    }
}
