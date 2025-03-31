package com.eventify.eventify.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public class UserWalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "0.00", message = "O saldo não pode ser negativo")
    @Column(nullable = false, columnDefinition = "DECIMAL(10,2) DEFAULT")
    private BigDecimal availableBalance = BigDecimal.ZERO;

    @DecimalMin(value = "0.00", message = "O saldo não pode ser negativo")
    @Column(nullable = false, columnDefinition = "DECIMAL(10,2) DEFAULT")
    private BigDecimal outstandingBalance = BigDecimal.ZERO;

    @OneToOne(mappedBy = "userWalletEntity")
    private UserEntity user;
}
