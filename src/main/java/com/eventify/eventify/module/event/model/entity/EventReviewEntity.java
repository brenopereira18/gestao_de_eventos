package com.eventify.eventify.module.event.model.entity;

import com.eventify.eventify.module.user.model.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_review")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity reviewer;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "event_review")
    private EventReview eventReview;

    private String Description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime dateReview;
}
