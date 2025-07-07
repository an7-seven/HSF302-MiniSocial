package com.hsf302.social.minisocial.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "follows", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"follower_id", "following_id"})
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Follow {
    @Id
    @Column(name = "follow_id")
    private UUID id = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @ManyToOne
    @JoinColumn(name = "following_id", nullable = false)
    private User following;

    private Timestamp createdAt = Timestamp.from(Instant.now());
}
