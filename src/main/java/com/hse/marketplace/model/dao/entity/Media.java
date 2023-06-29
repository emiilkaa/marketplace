package com.hse.marketplace.model.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "media")
@EqualsAndHashCode(of = {"reviewId", "url"})
public class Media implements Serializable {

    @Id
    @Column(updatable = false, name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mediaSequence")
    @SequenceGenerator(name = "mediaSequence", sequenceName = "seq_media", allocationSize = 1)
    private Long id;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "date_updated")
    private Date dateUpdated;

    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "url")
    private String url;

    @Column(name = "deleted")
    private Boolean deleted;

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
}
