package com.eventify.eventify.module.event.service;

import com.eventify.eventify.exceptions.ResourceNotFoundException;
import com.eventify.eventify.module.event.model.dto.CreateEventRequestDTO;
import com.eventify.eventify.module.event.model.dto.GetEventResponseDTO;
import com.eventify.eventify.module.event.model.dto.GetPendingEventResponseDTO;
import com.eventify.eventify.module.event.model.dto.UpdateEventRequestDTO;
import com.eventify.eventify.module.event.model.entity.EventCategory;
import com.eventify.eventify.module.event.model.entity.EventEntity;
import com.eventify.eventify.module.event.model.entity.FinancialOfTheEventEntity;
import com.eventify.eventify.module.event.model.entity.StatusReview;
import com.eventify.eventify.module.event.repository.EventRepository;
import com.eventify.eventify.module.event.service.mapper.EventCreateMapper;
import com.eventify.eventify.module.event.service.mapper.GetEventMapper;
import com.eventify.eventify.module.event.service.mapper.UpdateEventMapper;
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
    private EventCreateMapper eventCreateMapper;

    @Autowired
    private GetEventMapper getEventMapper;

    @Autowired
    private UpdateEventMapper updateEventMapper;

    @Transactional
    public GetEventResponseDTO createEvent(CreateEventRequestDTO createEventRequestDTO, UserEntity organizer) {
        FinancialOfTheEventEntity financial = new FinancialOfTheEventEntity();

        EventEntity event = eventCreateMapper.toEntity(createEventRequestDTO, financial, organizer);
        financial.setEvent(event);

        this.eventRepository.save(event);
        return this.eventCreateMapper.toResponse(event);
    }

    public GetEventResponseDTO getPublicEvent(Long id) {
        EventEntity event = eventRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Evento não foi encontrado")
        );
        return this.getEventMapper.toResponse(event);
    }

    public List<GetEventResponseDTO> getAllEvents(String name, String city, EventCategory eventCategory) {
        List<EventEntity> events = this.eventRepository.filterEvents(
            name,
            city,
            eventCategory != null ? eventCategory.name() : null);

        if (events.isEmpty()) {
            throw  new ResourceNotFoundException("Não foi encontrado nenhum evento");
        }
        return events.stream().map(this.getEventMapper::toResponse).toList();
    }

    public List<GetPendingEventResponseDTO> getPendingEvents() {
        List<EventEntity> events = this.eventRepository.findByStatusReview(StatusReview.PENDING);

        if (events.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum evento pendente no momento");
        }
        return events.stream().map(this.getEventMapper::toPendingResponse).toList();
    }

    public GetEventResponseDTO updateEvent(Long id, UpdateEventRequestDTO dto) {
        EventEntity event = this.eventRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Evento não encontrado")
        );
        this.updateEventMapper.updateEventFromDto(dto, event);
        this.eventRepository.save(event);

        return this.getEventMapper.toResponse(event);
    }
}
