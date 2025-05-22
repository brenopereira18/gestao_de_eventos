package com.eventify.eventify.module.event.model.dto;

import java.math.BigDecimal;

public record TicketPriceResponseDTO(Long id, BigDecimal priceForMen, BigDecimal priceForWomen, String sectorName, String lotName) {
}
