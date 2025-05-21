package com.eventify.eventify.module.event.service;

import com.eventify.eventify.exceptions.ResourceNotFoundException;
import com.eventify.eventify.module.event.model.dto.CreateEventDTO;
import com.eventify.eventify.module.event.model.dto.CreateTicketPriceDTO;
import com.eventify.eventify.module.event.model.entity.EventEntity;
import com.eventify.eventify.module.event.model.entity.FinancialOfTheEventEntity;
import com.eventify.eventify.module.event.repository.EventRepository;
import com.eventify.eventify.module.event.repository.FinancialOfTheEventRepository;
import com.eventify.eventify.module.sectorAndLot.model.entity.LotEntity;
import com.eventify.eventify.module.sectorAndLot.model.entity.LotSectorTicketEntity;
import com.eventify.eventify.module.sectorAndLot.model.entity.SectorEntity;
import com.eventify.eventify.module.user.model.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private FinancialOfTheEventRepository financialOfTheEventRepository;

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

    @Transactional
    public EventEntity createEvent(CreateEventDTO createEventDTO, UserEntity organizer) {
        FinancialOfTheEventEntity financial = new FinancialOfTheEventEntity();
        financial = this.financialOfTheEventRepository.save(financial);

        EventEntity event = EventEntity.builder()
            .name(createEventDTO.name())
            .startDate(createEventDTO.startDate())
            .endDate(createEventDTO.endDate())
            .salesEndDate(createEventDTO.startDate().minusHours(1))
            .eventCategory(createEventDTO.eventCategory())
            .description(createEventDTO.description())
            .address(createEventDTO.address())
            .city(createEventDTO.city())
            .state(createEventDTO.state())
            .financialOfTheEventEntity(financial)
            .organizer(organizer)
            .build();

        financial.setEvent(event);

        List<SectorEntity> sectors = createEventDTO.sectors().stream()
            .map(s -> {
                SectorEntity sector = SectorEntity.builder().name(s.name()).build();
                event.addSector(sector);
                return sector;
            }).toList();

        List<LotEntity> lots = createEventDTO.lots().stream()
            .map(l -> {
                LotEntity lot = LotEntity.builder().name(l.name()).startDate(l.startDate()).endDate(l.endDate()).totalNumberOfTickets(l.totalNumberOfTickets()).build();
                event.addLot(lot);
                return lot;
            }).toList();

        for (CreateTicketPriceDTO ticketPrice : createEventDTO.ticketPrices()) {
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

        return eventRepository.save(event);
    }
}
