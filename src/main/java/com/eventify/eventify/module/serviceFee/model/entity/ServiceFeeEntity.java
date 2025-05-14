package com.eventify.eventify.module.serviceFee.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "service_fee")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceFeeEntity {

    @Id
    private Long id = 1L;
    private BigDecimal serviceFee = BigDecimal.valueOf(0.1);
}
