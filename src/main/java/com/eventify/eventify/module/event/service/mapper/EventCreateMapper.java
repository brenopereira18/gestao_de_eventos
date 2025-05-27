package com.eventify.eventify.module.event.service.mapper;

import com.eventify.eventify.exceptions.ResourceNotFoundException;
import com.eventify.eventify.module.event.model.dto.*;
import com.eventify.eventify.module.event.model.entity.EventEntity;
import com.eventify.eventify.module.event.model.entity.FinancialOfTheEventEntity;
import com.eventify.eventify.module.event.model.entity.StatusReview;
import com.eventify.eventify.module.sectorAndLot.model.entity.LotEntity;
import com.eventify.eventify.module.sectorAndLot.model.entity.LotSectorTicketEntity;
import com.eventify.eventify.module.sectorAndLot.model.entity.SectorEntity;
import com.eventify.eventify.module.user.model.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventCreateMapper {

    public EventEntity toEntity(CreateEventRequestDTO dto, FinancialOfTheEventEntity financial, UserEntity organizer) {
        EventEntity event = EventEntity.builder()
            .name(dto.name())
            .startDate(dto.startDate())
            .endDate(dto.endDate())
            .salesEndDate(dto.startDate().minusHours(1))
            .eventCategory(dto.eventCategory())
            .description(dto.description())
            .address(dto.address())
            .city(dto.city())
            .state(dto.state())
            .financialOfTheEventEntity(financial)
            .organizer(organizer)
            .statusReview(StatusReview.PENDING)
            .build();

        List<SectorEntity> sectors = dto.sectors().stream()
            .map(s -> {
                SectorEntity sector = new SectorEntity();
                sector.setName(s.name());
                sector.setEvent(event);
                event.addSector(sector);
                return sector;
            }).toList();

        List<LotEntity> lots = dto.lots().stream()
            .map(l -> {
                LotEntity lot = LotEntity.builder().name(l.name()).startDate(l.startDate()).endDate(l.endDate()).totalNumberOfTickets(l.totalNumberOfTickets()).event(event).build();
                event.addLot(lot);
                return lot;
            }).toList();

        dto.ticketPrices().forEach(ticket -> {
            SectorEntity sector = findSectorByName(sectors, ticket.sectorName());
            LotEntity lot = findLotByName(lots, ticket.lotName());

            LotSectorTicketEntity lotSectorTicket = LotSectorTicketEntity.builder()
                .sector(sector)
                .lot(lot)
                .ticketForMen(ticket.priceForMen())
                .ticketForWomen(ticket.priceForWomen())
                .build();

            event.addLotSectorTicket(lotSectorTicket);
            sector.getLotSectorTickets().add(lotSectorTicket);
        });

        return event;
    }

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
                .map(sector -> SectorResponseDTO.builder()
                    .name(sector.getName())
                    .lotSectorTickets(sector.getLotSectorTickets().stream()
                        .map(ticket -> TicketPriceResponseDTO.builder()
                            .lotName(ticket.getLot().getName())
                            .priceForMen(ticket.getTicketForMen())
                            .priceForWomen(ticket.getTicketForWomen())
                            .build())
                        .toList())
                    .build())
                .toList())
            .build();
    }

    private SectorEntity findSectorByName(List<SectorEntity> sectors, String name) {
        return sectors.stream()
            .filter(s -> s.getName().equals(name))
            .findFirst()
            .orElseThrow(() ->
                new ResourceNotFoundException("Setor não encontrado: " + name)
            );
    }

    private LotEntity findLotByName(List<LotEntity> lots, String name) {
        return lots.stream()
            .filter(l -> l.getName().equals(name))
            .findFirst()
            .orElseThrow(() ->
                new ResourceNotFoundException("Lote não encontrado: " + name)
            );
    }
}
