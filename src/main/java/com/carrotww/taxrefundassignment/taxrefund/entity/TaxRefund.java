package com.carrotww.taxrefundassignment.taxrefund.entity;

import com.carrotww.taxrefundassignment.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
@Table(name = "tax_refund")
public class TaxRefund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private double calculatedRefund;
}
