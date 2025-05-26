package com.eventify.eventify.module.event.repository;

import com.eventify.eventify.module.event.model.dto.GetEventResponseDTO;
import com.eventify.eventify.module.event.model.entity.EventCategory;
import com.eventify.eventify.module.event.model.entity.EventEntity;
import com.eventify.eventify.module.event.model.entity.StatusReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    Optional<EventEntity> findById(Long id);
    Optional<EventEntity> findByStatusReview(StatusReview statusReview);

    @Query(value = """
        SELECT * FROM events
        WHERE status_review = 'APPROVED'
            AND (:name IS NULL OR LOWER(name) LIKE LOWER(CONCAT('%', :name, '%')))
            AND (:city IS NULL OR LOWER(city) = LOWER(:city))
            AND (:event_category IS NULL OR event_category = :event_category)
        """, nativeQuery = true)
    List<EventEntity> filterEvents(
        @Param("name") String name,
        @Param("city") String city,
        @Param("event_category") String eventCategory
    );

}
