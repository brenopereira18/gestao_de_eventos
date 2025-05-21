package com.eventify.eventify.module.sectorAndLot.model.entity;

import com.eventify.eventify.module.event.model.entity.EventEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "lot")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @Column(name = "start_date")
    @FutureOrPresent(message = "A data deve ser no futuro ou no presente")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    @FutureOrPresent(message = "A data deve ser no futuro ou no presente")
    private LocalDateTime endDate;

    @Column(name = "number_tickets_sold")
    @Min(value = 0, message = "Não pode ser um número negativo")
    private Integer numberOfTicketsSold = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    @JsonBackReference
    private EventEntity event;

    @Column(name = "total_number_tickets")
    @Min(value = 0, message = "Não pode ser um número negativo")
    private Integer totalNumberOfTickets;

    @Enumerated(EnumType.STRING)
    private StatusLot statusLot;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @AssertTrue(message = "A data final deve ser após a data inicial")
    public boolean isDateRangeValid() {
        return this.endDate.isAfter(this.startDate);
    }
    /*
    public boolean isAvailableForSale(LocalDateTime eventSalesEndDate) {
        LocalDateTime now = LocalDateTime.now();

        boolean withinLotPeriod = !now.isBefore(startDate) && !now.isAfter(endDate);

        boolean beforeEventSalesEnd = now.isBefore(eventSalesEndDate);

        boolean hasTickets = hasAvailableTicket();

        return withinLotPeriod && beforeEventSalesEnd && hasTickets;
    }

    public boolean hasAvailableTicket() {
        return this.numberOfTicketsSold < this.totalNumberOfTickets;
    }

    @PreUpdate
    @PrePersist
    public void updateStatus() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(this.startDate)) {
            this.statusLot = StatusLot.PENDING;
        } else if (isValid()) {
            this.statusLot = StatusLot.ACTIVE;
        } else {
            this.statusLot = StatusLot.INACTIVE;
        }
    } */
}
