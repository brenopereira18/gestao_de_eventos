package com.eventify.eventify.module.event.model.entity;

import com.eventify.eventify.module.ticket.model.entity.TicketEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "financial_event")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinancialOfTheEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "financialOfTheEventEntity")
    @JsonBackReference
    private EventEntity event;

    @Column(name = "total_amount",columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal totalAmountCollected = BigDecimal.ZERO;

    @Column(name = "total_value",columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal totalValueFees = BigDecimal.ZERO;

    @ElementCollection
    @CollectionTable(name = "earning_by_sector")
    @MapKeyJoinColumn(name = "sector_id")
    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private Map<Long, BigDecimal> earningBySector = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "earning_by_lot")
    @MapKeyJoinColumn(name = "lot_id")
    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private Map<Long, BigDecimal> earningByLot = new HashMap<>();

    @OneToMany(mappedBy = "financialEvent")
    private List<TicketEntity> tickets = new ArrayList<>();

}
