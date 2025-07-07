package com.hsf302.social.minisocial.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"post_id", "user_id"})
})
public class Like {

    @Id
    @Column(name = "like_id")
    private UUID id = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Timestamp createAt = Timestamp.from(Instant.now());
}
