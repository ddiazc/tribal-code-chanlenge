package com.tribal.challenge.authorizer.domain.model.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "credit_line_authorization")
public class CreditLineEntity {
    @Id
    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "founding_type")
    private String foundingType;

    @Column(name = "cash_balance")
    private BigDecimal cashBalance;

    @Column(name = "monthly_revenue")
    private BigDecimal monthlyRevenue;

    @Column(name = "requested_credit_line")
    private BigDecimal requestedCreditLine;

    @Column(name = "requested_date")
    private LocalDateTime requestedDate;

    @Column(name = "status")
    private String status;

    @Builder.Default
    @Column(name = "failed_attempts")
    private int failedAttempts = 0;

}
