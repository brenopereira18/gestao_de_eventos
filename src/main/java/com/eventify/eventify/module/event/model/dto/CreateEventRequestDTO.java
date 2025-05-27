package com.eventify.eventify.module.event.model.dto;

import com.eventify.eventify.module.event.model.entity.EventCategory;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CreateEventRequestDTO(
    String name,
    LocalDateTime startDate,
    LocalDateTime endDate,
    EventCategory eventCategory,
    String description,
    String address,
    String city,
    String state,
    List<SectorResponseDTO> sectors,
    List<LotCreateEventDTO> lots,
    List<TicketPriceRequestDTO> ticketPrices
) {
}
