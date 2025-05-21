package com.eventify.eventify.module.event.controller;

import com.eventify.eventify.module.event.model.dto.CreateEventDTO;
import com.eventify.eventify.module.event.model.entity.EventEntity;
import com.eventify.eventify.module.event.service.EventService;
import com.eventify.eventify.module.user.model.entity.Gender;
import com.eventify.eventify.module.user.model.entity.UserEntity;
import com.eventify.eventify.module.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity<EventEntity> createEvent(@Valid @RequestBody CreateEventDTO createEventDTO) {
        UserEntity user = userRepository.findByCpf("127.890.938-24").orElseThrow();
        EventEntity event = eventService.createEvent(createEventDTO, user);
        return ResponseEntity.ok().body(event);
    }
}
