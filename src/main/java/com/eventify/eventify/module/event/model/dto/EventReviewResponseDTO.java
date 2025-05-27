package com.eventify.eventify.module.event.model.dto;

import com.eventify.eventify.module.event.model.entity.StatusReview;
import com.eventify.eventify.module.user.model.entity.UserEntity;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EventReviewResponseDTO(
    UserEntity reviewer,
    StatusReview statusReview,
    String description,
    LocalDateTime dateReview
) {}
