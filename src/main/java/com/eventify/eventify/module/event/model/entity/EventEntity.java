package com.eventify.eventify.module.event.model.entity;

import com.eventify.eventify.module.sectorAndLot.model.entity.LotEntity;
import com.eventify.eventify.module.sectorAndLot.model.entity.SectorEntity;
import com.eventify.eventify.module.ticket.model.entity.DiscountCouponEntity;
import com.eventify.eventify.module.user.model.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private List<SectorEntity> sectors = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LotEntity> lots = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DiscountCouponEntity> discountCoupons = new ArrayList<>();

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
    private StatusReview statusReview = StatusReview.PENDING;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventReviewEntity> eventReviews = new ArrayList<>();

    @Future
    @Column(name = "sales_end_date", nullable = false)
    @NotNull
    private LocalDateTime salesEndDate;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "financial_id", nullable = false)
    private FinancialOfTheEventEntity financialOfTheEventEntity;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @AssertTrue
    public boolean isItAvailable() {
        return !LocalDateTime.now().isAfter(this.startDate.plusHours(2));
    }

    public void addSector(SectorEntity sector) {
        this.sectors.add(sector);
        sector.setEvent(this);
    }

    public void removeSector(SectorEntity sector) {
        this.sectors.remove(sector);
        sector.setEvent(null);
    }

    public void addLot(LotEntity lot) {
        this.lots.add(lot);
        lot.setEvent(this);
    }

    public void removeLot(LotEntity lot) {
        this.lots.remove(lot);
        lot.setEvent(null);
    }

    public void addDiscountCoupon(DiscountCouponEntity coupon) {
        this.discountCoupons.add(coupon);
        coupon.setEvent(this);
    }

    public void removeDiscountCoupon(DiscountCouponEntity coupon) {
        this.discountCoupons.remove(coupon);
        coupon.setEvent(null);
    }

    public void addEventReview(EventReviewEntity review) {
        this.eventReviews.add(review);
        review.setEvent(this);
    }
}
