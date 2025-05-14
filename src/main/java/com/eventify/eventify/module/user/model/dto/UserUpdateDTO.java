package com.eventify.eventify.module.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    @Size(min = 8, max = 16)
    private String password;

    @Pattern(regexp = "^(\\d{2})?\\s?9\\d{4}-?\\d{4}$", message = "Número de celular inválido. Use o formato XX 9XXXX-XXXX ou XX 9XXXXXXXX")
    private String phoneNumber;

    @Email
    private String email;
}
