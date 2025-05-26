package com.eventify.eventify.module.event.service.mapper;

import com.eventify.eventify.module.event.model.dto.*;
import com.eventify.eventify.module.event.model.entity.EventEntity;
import org.springframework.stereotype.Component;

@Component
public class GetEventMapper {

    public GetEventResponseDTO toResponse(EventEntity entity) {
        return GetEventResponseDTO.builder()
            .name(entity.getName())
            .startDate(entity.getStartDate())
            .endDate(entity.getEndDate())
            .eventCategory(entity.getEventCategory())
            .description(entity.getDescription())
            .address(entity.getAddress())
            .city(entity.getCity())
            .state(entity.getState())
            .sectors(entity.getSectors().stream()
                .map(s -> new SectorResponseDTO(s.getName()))
                .toList())
            .ticketPrices(entity.getLotSectorTickets().stream()
                .map(t -> new TicketPriceResponseDTO(t.getTicketForMen(), t.getTicketForWomen(), t.getSector().getName(), t.getLot().getName()))
                .toList())
            .build();
    }
}
