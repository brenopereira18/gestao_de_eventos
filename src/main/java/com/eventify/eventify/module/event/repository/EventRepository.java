package com.eventify.eventify.module.event.repository;

import com.eventify.eventify.module.event.model.entity.EventCategory;
import com.eventify.eventify.module.event.model.entity.EventEntity;
import com.eventify.eventify.module.event.model.entity.StatusReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    Optional<EventEntity> findByName(String name);
    Optional<EventEntity> findByStartDate(LocalDateTime date);
    Optional<EventEntity> findByEventCategory(EventCategory category);
    Optional<EventEntity> findByStatusReview(StatusReview statusReview);
}
