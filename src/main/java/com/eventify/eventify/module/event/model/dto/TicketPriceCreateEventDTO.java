package com.eventify.eventify.module.event.model.dto;

import java.math.BigDecimal;

public record TicketPriceCreateEventDTO(Long id, BigDecimal priceForMen, BigDecimal priceForWomen, String sectorName, String lotName) {
}
