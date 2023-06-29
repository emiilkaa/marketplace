package com.hse.marketplace.model.dao.entity;

import com.hse.marketplace.model.dto.response.ProductDto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@EqualsAndHashCode(of = "id")
public class Product implements Serializable {

    @Id
    @Column(updatable = false, name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSequence")
    @SequenceGenerator(name = "productSequence", sequenceName = "seq_product", allocationSize = 1)
    private Long id;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "date_updated")
    private Date dateUpdated;

    @Column(name = "available_quantity")
    private Integer availableQuantity;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "product_name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private List<Review> reviews;

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

    public ProductDto toProductDto() {
        OptionalDouble avg = reviews.stream().mapToInt(Review::getRating).average();
        BigDecimal averageRating = avg.isPresent() ?
                BigDecimal.valueOf(avg.getAsDouble()).setScale(2, RoundingMode.HALF_UP) : null;
        return ProductDto.builder()
                .name(name)
                .description(description)
                .price(price)
                .availableQuantity(availableQuantity)
                .reviews(reviews.stream().map(Review::toReviewDto).collect(Collectors.toList()))
                .averageRating(averageRating)
                .build();
    }
}
