package com.eventify.eventify.module.event.service.mapper;

import com.eventify.eventify.exceptions.ResourceNotFoundException;
import com.eventify.eventify.module.event.model.dto.LotCreateEventDTO;
import com.eventify.eventify.module.event.model.dto.SectorCreateEventDTO;
import com.eventify.eventify.module.event.model.dto.TicketPriceCreateEventDTO;
import com.eventify.eventify.module.event.model.dto.CreateEventResponseDTO;
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

    public EventEntity toEntity(CreateEventResponseDTO dto, FinancialOfTheEventEntity financial, UserEntity organizer) {
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
            .build();

        List<SectorEntity> sectors = dto.sectors().stream()
            .map(s -> {
                SectorEntity sector = SectorEntity.builder().name(s.name()).event(event).build();
                event.addSector(sector);
                return sector;
            }).toList();

        List<LotEntity> lots = dto.lots().stream()
            .map(l -> {
                LotEntity lot = LotEntity.builder().name(l.name()).startDate(l.startDate()).endDate(l.endDate()).totalNumberOfTickets(l.totalNumberOfTickets()).event(event).build();
                event.addLot(lot);
                return lot;
            }).toList();

        for (TicketPriceCreateEventDTO ticketPrice : dto.ticketPrices()) {
            SectorEntity sector = findSectorByName(sectors, ticketPrice.sectorName());
            LotEntity lot = findLotByName(lots, ticketPrice.lotName());

            LotSectorTicketEntity lotSectorTicket = LotSectorTicketEntity.builder()
                .sector(sector)
                .lot(lot)
                .ticketForMen(ticketPrice.priceForMen())
                .ticketForWomen(ticketPrice.priceForWomen())
                .build();

            event.addLotSectorTicket(lotSectorTicket);
        }

        return event;
    }

    public CreateEventResponseDTO toResponse(EventEntity entity) {
        return CreateEventResponseDTO.builder()
            .id(entity.getId())
            .name(entity.getName())
            .startDate(entity.getStartDate())
            .endDate(entity.getEndDate())
            .eventCategory(entity.getEventCategory())
            .description(entity.getDescription())
            .address(entity.getAddress())
            .city(entity.getCity())
            .state(entity.getState())
            .sectors(entity.getSectors().stream()
                .map(s -> new SectorCreateEventDTO(s.getId(), s.getName()))
                .toList())
            .lots(entity.getLots().stream()
                .map(l -> new LotCreateEventDTO(l.getId(), l.getName(), l.getStartDate(), l.getEndDate(), l.getTotalNumberOfTickets()))
                .toList())
            .ticketPrices(entity.getLotSectorTickets().stream()
                .map(t -> new TicketPriceCreateEventDTO(t.getId(), t.getTicketForMen(), t.getTicketForWomen(), t.getSector().getName(), t.getLot().getName()))
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
