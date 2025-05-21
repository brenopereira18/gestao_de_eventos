package com.eventify.eventify.module.event.model.dto;

import com.eventify.eventify.module.event.model.entity.EventCategory;

import java.time.LocalDateTime;
import java.util.List;

public record CreateEventDTO(
    String name,
    LocalDateTime startDate,
    LocalDateTime endDate,
    EventCategory eventCategory,
    String description,
    String address,
    String city,
    String state,
    List<CreateSectorDTO> sectors,
    List<CreateLotDTO> lots,
    List<CreateTicketPriceDTO> ticketPrices
) {}
