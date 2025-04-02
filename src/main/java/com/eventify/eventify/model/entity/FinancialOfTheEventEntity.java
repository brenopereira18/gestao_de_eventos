package com.eventify.eventify.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "finacial_event")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinancialOfTheEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "financialOfTheEventEntity")
    private EventEntity event;

    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal totalAmountCollected = BigDecimal.ZERO;

    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal totalValueFees = BigDecimal.ZERO;

    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private Map<Long, BigDecimal> collectionBySector;

    @Column(columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private Map<Long, BigDecimal> collectionByLot;
}
