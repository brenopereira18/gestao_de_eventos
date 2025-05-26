package com.eventify.eventify.module.event.model.dto;

import java.math.BigDecimal;

public record TicketPriceResponseDTO(BigDecimal priceForMen, BigDecimal priceForWomen, String sectorName, String lotName) {
}
