package com.eventify.eventify.module.user.model.entity;

import com.eventify.eventify.module.cashWithdrawal.model.entity.CashWithdrawalEntity;
import com.eventify.eventify.module.event.model.entity.EventEntity;
import com.eventify.eventify.module.ticket.model.entity.TicketEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String name;

    @NotBlank
    @Column(nullable = false, length = 50, unique = true)
    @Email
    private String email;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 8, max = 16)
    private String password;

    @NotBlank
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF deve estar no formato XXX.XXX.XXX-XX")
    private String cpf;

    @NotBlank
    @Column(name = "phone_number", nullable = false, unique = true)
    @Pattern(regexp = "^(\\d{2})?\\s?9\\d{4}-?\\d{4}$", message = "Número de celular inválido. Use o formato XX 9XXXX-XXXX ou XX 9XXXXXXXX")
    private String phoneNumber;

    @NotBlank
    @Column(name = "user_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "wallet_id")
    private UserWalletEntity userWalletEntity;

    @OneToMany(mappedBy = "organizer", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<EventEntity> events = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "withdrawal_id")
    private List<CashWithdrawalEntity> cashWithdrawal = new ArrayList<>();

    @OneToMany
    private List<TicketEntity> tickets = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;


}
