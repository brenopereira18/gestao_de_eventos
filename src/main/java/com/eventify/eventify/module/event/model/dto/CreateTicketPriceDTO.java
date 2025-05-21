package com.eventify.eventify.module.event.model.dto;

import java.math.BigDecimal;

public record CreateTicketPriceDTO(BigDecimal priceForMen, BigDecimal priceForWomen, String sectorName, String lotName) {
}
