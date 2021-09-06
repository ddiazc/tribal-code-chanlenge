package com.tribal.challenge.authorizer.domain.model.core;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CreditLineCore {
    private String taxId;
    private String foundingType;
    private BigDecimal cashBalance;
    private BigDecimal monthlyRevenue;
    private BigDecimal requestedCreditLine;
    private LocalDateTime requestedDate;
    private String status;
    private int failedAttempts;
}
