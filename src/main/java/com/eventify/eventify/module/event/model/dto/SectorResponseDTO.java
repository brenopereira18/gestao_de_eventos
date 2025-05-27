package com.eventify.eventify.module.event.model.dto;

import com.eventify.eventify.module.sectorAndLot.model.entity.LotSectorTicketEntity;
import lombok.Builder;

import java.util.List;

@Builder
public record SectorResponseDTO(String name, List<TicketPriceResponseDTO> lotSectorTickets) {
}
