package com.eventify.eventify.module.event.model.dto;

import java.time.LocalDateTime;

public record CreateLotDTO(String name, LocalDateTime startDate, LocalDateTime endDate, Integer totalNumberOfTickets) {
}
