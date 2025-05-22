package com.eventify.eventify.module.event.model.dto;

import java.time.LocalDateTime;

public record LotResponseDTO(Long id, String name, LocalDateTime startDate, LocalDateTime endDate, Integer totalNumberOfTickets) {
}
