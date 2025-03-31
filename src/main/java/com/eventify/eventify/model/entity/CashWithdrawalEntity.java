package com.eventify.eventify.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CashWithdrawalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "cashWithdrawal", optional = false)
    private UserEntity user;

    @Column(name = "amount_withdrawn" , nullable = false)
    @NotNull
    @DecimalMin(value = "20.00", message = "O valor mínimo para saque é de R$20,00")
    private BigDecimal amountWithdrawn;

    @CreationTimestamp
    @Column(name = "request_date")
    private LocalDateTime requestDate;

    @NotBlank
    @Column(name = "status_withdrawal", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusWithdrawal statusWithdrawal;

}
