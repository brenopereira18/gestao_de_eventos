package com.eventify.eventify.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^(\\d{2})?\\s?9\\d{4}-?\\d{4}$", message = "Número de celular inválido. Use o formato XX 9XXXX-XXXX ou XX 9XXXXXXXX")
    private String phoneNumber;

    @NotBlank
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Embedded
    private ParticipantProfileEntity participantProfileEntity;

    @Embedded
    private EventOrganizerProfileEntity eventOrganizerProfile;
}
