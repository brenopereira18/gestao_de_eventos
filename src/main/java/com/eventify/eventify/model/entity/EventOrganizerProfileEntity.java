package com.eventify.eventify.model.entity;

import jakarta.persistence.Embeddable;

import java.util.List;

@Embeddable
public class EventOrganizerProfileEntity {

    private OrganizerWalletEntity organizerWalletEntity;
    private List<EventEntity> events;
}
