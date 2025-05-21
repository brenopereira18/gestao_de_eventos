package com.eventify.eventify.module.sectorAndLot.model.entity;

import com.eventify.eventify.module.event.model.entity.EventEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    @JsonBackReference
    private EventEntity event;

    public void decrementAvailableTickets() {
        if (this.numberOfTicketsAvailable <= 0) {
            throw new IllegalStateException("Não há ingressos para disponiveis para este setor/lote");
        }
        this.numberOfTicketsAvailable--;
        this.lot.setNumberOfTicketsSold(this.lot.getNumberOfTicketsSold() + 1);
    }
}
