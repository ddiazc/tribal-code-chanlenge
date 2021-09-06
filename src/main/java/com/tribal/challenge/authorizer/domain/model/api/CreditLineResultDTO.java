package com.tribal.challenge.authorizer.domain.model.api;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreditLineResultDTO {
    private String status;
    private BigDecimal creditLineAccepted;
}
