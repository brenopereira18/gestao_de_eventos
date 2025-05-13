package com.eventify.eventify.module.ticket.model.entity;

import com.eventify.eventify.module.event.model.entity.EventEntity;
import com.eventify.eventify.module.event.model.entity.FinancialOfTheEventEntity;
import com.eventify.eventify.module.sectorAndLot.model.entity.LotSectorTicketEntity;
import com.eventify.eventify.module.serviceFee.model.entity.ServiceFeeEntity;
import com.eventify.eventify.module.user.model.entity.Gender;
import com.eventify.eventify.module.user.model.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_holder_id")
    private UserEntity ticketHolder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_buyer_id", nullable = false)
    @NotNull(message = "O comprador do ingresso é obrigatório")
    private UserEntity ticketBuyer;

    @OneToOne
    @JoinColumn(name = "coupon_id")
    private DiscountCouponEntity discountCoupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @Column(name = "qr_code", unique = true, length = 500)
    @NotBlank(message = "QR Code não pode ser vazio")
    private String qrCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status do ingresso é obrigatório")
    private StatusTicket statusTicket;

    @OneToOne
    @JoinColumn(name = "service_fee_id", nullable = false)
    private ServiceFeeEntity serviceFee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_sector_ticket_id", nullable = false)
    @NotNull(message = "O lote/setor do ingresso é obrigatório")
    private LotSectorTicketEntity lotSectorTicket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "financial_event_id")
    private FinancialOfTheEventEntity financialEvent;

    @Column(name = "ticket_code", unique = true, length = 50)
    @NotBlank(message = "Código do ingresso é obrigatório")
    @Size(min = 5, max = 50, message = "Código deve ter 5 a 50 caracteres")
    private String ticketCode;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public BigDecimal finalTicketValue() {
        BigDecimal basePrice = getBasePriceByGender();
        BigDecimal discountedValue = (this.discountCoupon != null)
            ? applyDiscount(basePrice, this.discountCoupon)
            : basePrice;

        BigDecimal fee = calculateFee(basePrice);
        return discountedValue.add(fee).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateFee(BigDecimal basePrice) {
        BigDecimal feeFactor = BigDecimal.ONE.add(this.serviceFee.getServiceFee());
        return basePrice.multiply(feeFactor);
    }

    private BigDecimal getBasePriceByGender() {
        return this.ticketHolder.getGender() == Gender.MASCULINO
            ? this.lotSectorTicket.getTicketForMen()
            : this.lotSectorTicket.getTicketForWomen();
    }

    private BigDecimal applyDiscount(BigDecimal basePrice, DiscountCouponEntity coupon) {
        if (this.discountCoupon.isPercentageDiscount()) {
            BigDecimal discountFactor = BigDecimal.ONE
                .subtract(coupon.getDiscountAmount().divide(BigDecimal.valueOf(100)));
            return basePrice.multiply(discountFactor);
        } else {
            return basePrice.subtract(coupon.getDiscountAmount());
        }
    }
}
