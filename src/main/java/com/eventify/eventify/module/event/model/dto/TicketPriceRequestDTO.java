package com.eventify.eventify.module.event.model.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TicketPriceRequestDTO(
    BigDecimal priceForMen,
    BigDecimal priceForWomen,
    String lotName,
    String sectorName
) {
}
