package com.tribal.challenge.authorizer.domain.model.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RecommendedCreditLine {
    CASH_BALANCE(1d/3d),
    MONTHLY_REVENUE(1d/5d);
    private double ratio;
}
