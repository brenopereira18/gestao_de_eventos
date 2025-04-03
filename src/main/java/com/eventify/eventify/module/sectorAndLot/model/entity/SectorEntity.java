package com.eventify.eventify.module.sectorAndLot.model.entity;

import com.eventify.eventify.module.event.model.entity.EventEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "sector")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SectorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "O nome não pode estar vazio")
    private String name;

    @OneToMany(mappedBy = "sector")
    private List<LotSectorTicketEntity> lotSectorTickets;
}
