package com.tribal.challenge.authorizer.domain.model.core;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreditLineResultCore {
    private String status;
    private BigDecimal creditLineAccepted;
}
