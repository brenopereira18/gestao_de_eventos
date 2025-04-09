package com.eventify.eventify.module.ticket.model.entity;

import com.eventify.eventify.module.event.model.entity.EventEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "discount_coupon")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_code", nullable = false, unique = true, length = 50)
    @NotBlank(message = "O código do cupom é obrigatório")
    @Size(max = 50, message = "O código deve ter no máximo 50 caracteres")
    private String couponCode;

    @DecimalMin(value = "0.01", message = "O valor de desconto deve ser maior do que zero")
    @Column(name = "discount_amount", nullable = false)
    private BigDecimal discountAmount;

    @NotNull
    @Column(name = "is_percentage_discount", nullable = false)
    private boolean isPercentageDiscount;

    @Future(message = "A data de validade deve ser no futuro")
    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @NotNull
    @Positive(message = "A quantidade de cupons deve ser maior que zero")
    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @NotNull
    @Min(value = 0)
    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public void decrementAmountOfCouponAvailable() {
        if (this.availableQuantity <= 0) {
            throw new IllegalStateException("Cupom esgotado");
        }
        this.availableQuantity--;
    }

    public void incrementAmountOfCouponAvailable() {
        if (this.availableQuantity >= this.totalQuantity) {
            throw new IllegalStateException("Quantidade disponível não pode exceder o total");
        }
        this.availableQuantity++;
    }

    @AssertTrue(message = "O cupom deve estar dentro da validade e com estoque disponível")
    public boolean isValid() {
        return !LocalDateTime.now().isAfter(this.expiryDate) && this.availableQuantity > 0;
    }
}
