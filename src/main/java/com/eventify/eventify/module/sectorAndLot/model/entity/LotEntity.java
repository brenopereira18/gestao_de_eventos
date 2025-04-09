package com.eventify.eventify.module.sectorAndLot.model.entity;

import com.eventify.eventify.module.event.model.entity.EventEntity;
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

    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return !now.isBefore(startDate) && !now.isAfter(endDate);
    }

    public boolean hasAvailableTicket() {
        return this.numberOfTicketsSold < this.totalNumberOfTickets;
    }

    @AssertTrue(message = "O lote não está valido para venda")
    public boolean isValid() {
        return isActive() && hasAvailableTicket();
    }

    @PreUpdate
    @PrePersist
    public void updateStatus() {
        if (LocalDateTime.now().isBefore(this.startDate)) {
            this.statusLot = StatusLot.PENDING;
        } else if (isValid()) {
            this.statusLot = StatusLot.ACTIVE;
        } else {
            this.statusLot = StatusLot.INACTIVE;
        }
    }
}
