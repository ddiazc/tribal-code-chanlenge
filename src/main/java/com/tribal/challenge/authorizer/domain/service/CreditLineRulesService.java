package com.tribal.challenge.authorizer.domain.service;

import com.tribal.challenge.authorizer.domain.model.core.CreditLineCore;

import java.math.BigDecimal;

public interface CreditLineRulesService {

    BigDecimal getCashBalanceCreditLine(CreditLineCore creditLineCore);

    BigDecimal getMonthlyRevenueCreditLine(CreditLineCore creditLineCore);

    BigDecimal getMaxRecommendedCreditLine(CreditLineCore creditLineCore);
}
