package com.eventify.eventify.module.event.controller;

import com.eventify.eventify.module.event.model.dto.CreateEventRequestDTO;
import com.eventify.eventify.module.event.model.dto.GetEventResponseDTO;
import com.eventify.eventify.module.event.model.dto.GetPendingEventResponseDTO;
import com.eventify.eventify.module.event.model.dto.UpdateEventRequestDTO;
import com.eventify.eventify.module.event.model.entity.EventCategory;
import com.eventify.eventify.module.event.model.entity.EventEntity;
import com.eventify.eventify.module.event.service.EventService;
import com.eventify.eventify.module.user.model.entity.UserEntity;
import com.eventify.eventify.module.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<GetEventResponseDTO> createEvent(@Valid @RequestBody CreateEventRequestDTO createEventRequestDTO) {
        UserEntity user = userRepository.findByCpf("127.890.938-24").orElseThrow();
        GetEventResponseDTO event = this.eventService.createEvent(createEventRequestDTO, user);
        return ResponseEntity.ok().body(event);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetEventResponseDTO> getPublicEvent(@PathVariable Long id) {
        GetEventResponseDTO eventResponse = this.eventService.getPublicEvent(id);
        return ResponseEntity.ok().body(eventResponse);
    }

    @GetMapping
    public ResponseEntity<List<GetEventResponseDTO>> getAllEvents(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String city,
        @RequestParam(required = false) EventCategory eventCategory
        ) {
        List<GetEventResponseDTO> events = this.eventService.getAllEvents(name, city, eventCategory);

        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(events);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<GetPendingEventResponseDTO>> getPendingEvents() {
        List<GetPendingEventResponseDTO> events = this.eventService.getPendingEvents();

        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(events);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<GetEventResponseDTO> updateEvent(@Valid @PathVariable Long id, @RequestBody UpdateEventRequestDTO updateEventRequest) {
        GetEventResponseDTO updateEvent = this.eventService.updateEvent(id, updateEventRequest);
        return ResponseEntity.ok().body(updateEvent);
    }
}
