package com.eventify.eventify.model.entity;

import jakarta.persistence.Embeddable;

import java.util.List;

@Embeddable
public class ParticipantProfileEntity {

    private List<IngressoEntity> tickets;
}
