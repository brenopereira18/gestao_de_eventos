package com.eventify.eventify.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "addres")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String street;

    @Column(nullable = false, name = "name_place")
    @NotBlank
    private String nameOfThePlace;

    @Column(nullable = false)
    @NotBlank
    private String Neighborhood;

    @Column(nullable = false)
    @NotBlank
    private String city;

    @Column(nullable = false)
    @NotBlank
    private String state;
}
