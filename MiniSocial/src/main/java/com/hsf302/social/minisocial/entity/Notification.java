package com.hsf302.social.minisocial.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @Column(name = "notification_id")
    private UUID id =  UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    @Column(length = 50, nullable = false)
    private NotificationType type; // LIKE, COMMENT, FOLLOW

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    private boolean isRead = false;
    private Timestamp createdAt = Timestamp.from(Instant.now());

    public enum NotificationType {
        LIKE,
        COMMENT,
        FOLLOW
    }

}
