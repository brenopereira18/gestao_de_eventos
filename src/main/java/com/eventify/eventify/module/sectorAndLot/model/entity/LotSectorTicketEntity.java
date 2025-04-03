package com.eventify.eventify.module.sectorAndLot.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table(name = "lotSectorTicket")
@AllArgsConstructor
@NoArgsConstructor
public class LotSectorTicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    @Column(precision = 10, scale = 2, name = "ticket_for_men")
    private BigDecimal ticketForMen;

    @NotNull
    @Positive
    @Column(precision = 10, scale = 2, name = "ticket_for_women")
    private BigDecimal ticketForWomen;

    @Min(value = 0)
    @Column(name = "number_tickets_available")
    private Integer numberOfTicketsAvailable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id", nullable = false)
    private SectorEntity sector;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id", nullable = false)
    private LotEntity lot;
}
