package com.eventify.eventify.module.event.model.dto;

import com.eventify.eventify.module.event.model.entity.EventCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateEventRequestDTO {
    LocalDateTime startDate;
    LocalDateTime endDate;
    String coverPhotoUrl;
    EventCategory eventCategory;
    String description;
    String address;
    String city;
    String state;
    String socialNetworkOfTheEvent;
    LocalDateTime salesEndDate;
}
