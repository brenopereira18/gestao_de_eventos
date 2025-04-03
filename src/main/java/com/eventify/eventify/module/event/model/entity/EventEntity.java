package com.eventify.eventify.module.event.model.entity;

import com.eventify.eventify.module.sectorAndLot.model.entity.LotEntity;
import com.eventify.eventify.module.sectorAndLot.model.entity.SectorEntity;
import com.eventify.eventify.module.user.model.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Future
    @Column(name = "start_date", nullable = false)
    @NotNull
    private LocalDateTime startDate;

    @Future
    @Column(name = "end_date", nullable = false)
    @NotNull
    private LocalDateTime endDate;

    @Column(name = "cover_photo_URL")
    private String coverPhotoUrl;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EventCategory eventCategory;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SectorEntity> sectors;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LotEntity> lots;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    @NotNull
    private AddressEntity address;

    @Column(nullable = false)
    @NotNull
    private String description;

    @Column(name = "social_network_event")
    private String socialNetworkOfTheEvent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organizer_id", nullable = false)
    private UserEntity organizer;

    @Enumerated(EnumType.STRING)
    private EventReview eventReview = EventReview.PENDENTE;

    @Future
    @Column(name = "sales_end_date", nullable = false)
    @NotNull
    private LocalDateTime salesEndDate;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "financial_id", nullable = false)
    private FinancialOfTheEventEntity financialOfTheEventEntity;
}
