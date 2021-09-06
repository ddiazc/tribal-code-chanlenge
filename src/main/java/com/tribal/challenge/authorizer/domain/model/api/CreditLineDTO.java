package com.tribal.challenge.authorizer.domain.model.api;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CreditLineDTO {
    @NotBlank
    private String taxId;
    @NotBlank
    private String foundingType;
    @Positive
    private BigDecimal cashBalance;
    @Positive
    private BigDecimal monthlyRevenue;
    @Positive
    private BigDecimal requestedCreditLine;
    @NotNull
    private LocalDateTime requestedDate;
}
