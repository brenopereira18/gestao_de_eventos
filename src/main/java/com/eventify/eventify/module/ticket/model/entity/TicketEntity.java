package com.eventify.eventify.module.ticket.model.entity;

import com.eventify.eventify.module.event.model.entity.EventEntity;
import com.eventify.eventify.module.sectorAndLot.model.entity.LotSectorTicketEntity;
import com.eventify.eventify.module.user.model.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_holder_id")
    private UserEntity ticketHolder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_buyer_id", nullable = false)
    @NotNull(message = "O comprador do ingresso é obrigatório")
    private UserEntity ticketBuyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @Column(name = "qr_code", unique = true, length = 500)
    @NotBlank(message = "QR Code não pode ser vazio")
    private String qrCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status do ingresso é obrigatório")
    private StatusTicket statusTicket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_sector_ticket_id", nullable = false)
    @NotNull(message = "O lote/setor do ingresso é obrigatório")
    private LotSectorTicketEntity lotSectorTicket;

    @Column(name = "ticket_code", unique = true, length = 50)
    @NotBlank(message = "Código do ingresso é obrigatório")
    @Size(min = 5, max = 50, message = "Código deve ter 5 a 50 caracteres")
    private String ticketCode;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
