package com.eventify.eventify.module.user.model.dto;

import com.eventify.eventify.module.user.model.entity.Gender;
import lombok.Builder;

@Builder
public record UserResponseDTO(
    Long id,
    String name,
    String email,
    String cpf,
    String phoneNumber,
    Gender gender
) {}
