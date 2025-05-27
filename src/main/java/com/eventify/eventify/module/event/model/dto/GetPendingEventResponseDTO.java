package com.eventify.eventify.module.event.model.dto;

import com.eventify.eventify.module.event.model.entity.EventCategory;
import com.eventify.eventify.module.user.model.dto.UserResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public record GetPendingEventResponseDTO(
    Long id,
    String name,
    LocalDateTime startDate,
    LocalDateTime endDate,
    String coverPhotoUrl,
    String socialNetworkOfTheEvent,
    EventCategory eventCategory,
    String description,
    String address,
    String city,
    String state,
    List<SectorResponseDTO> sectors,
    UserResponseDTO organizer,
    List<EventReviewResponseDTO> reviews,
    LocalDateTime createDate
) {
}
