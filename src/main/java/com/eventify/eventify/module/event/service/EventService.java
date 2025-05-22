package com.eventify.eventify.module.event.service;

import com.eventify.eventify.exceptions.ResourceNotFoundException;
import com.eventify.eventify.module.event.model.dto.EventResponseDTO;
import com.eventify.eventify.module.event.model.dto.TicketPriceResponseDTO;
import com.eventify.eventify.module.event.model.entity.EventEntity;
import com.eventify.eventify.module.event.model.entity.FinancialOfTheEventEntity;
import com.eventify.eventify.module.event.repository.EventRepository;
import com.eventify.eventify.module.event.repository.FinancialOfTheEventRepository;
import com.eventify.eventify.module.event.service.mapper.EventMapper;
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

    @Autowired
    private EventMapper eventMapper;

    @Transactional
    public EventResponseDTO createEvent(EventResponseDTO eventResponseDTO, UserEntity organizer) {
        FinancialOfTheEventEntity financial = new FinancialOfTheEventEntity();

        EventEntity event = eventMapper.toEntity(eventResponseDTO, financial, organizer);
        financial.setEvent(event);

        eventRepository.save(event);
        return eventMapper.toResponse(event);
    }
}
