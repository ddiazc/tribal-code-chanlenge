package com.tribal.challenge.authorizer.core.service;

import com.tribal.challenge.authorizer.domain.model.core.CreditLineCore;
import com.tribal.challenge.authorizer.domain.service.CreditLineRulesService;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

@Service
public class CreditLineRulesServiceImpl implements CreditLineRulesService {

    private static final BigDecimal THREE = BigDecimal.valueOf(3);
    private static final BigDecimal FIVE = BigDecimal.valueOf(5);
    private static final int SCALE = 2;

    @Override
    public BigDecimal getCashBalanceCreditLine(CreditLineCore creditLineCore) {
        return creditLineCore.getCashBalance().divide(THREE, SCALE, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal getMonthlyRevenueCreditLine(CreditLineCore creditLineCore) {
        return creditLineCore.getMonthlyRevenue().divide(FIVE, SCALE, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal getMaxRecommendedCreditLine(CreditLineCore creditLineCore) {
        final BigDecimal cashBalanceCreditLine = this.getCashBalanceCreditLine(creditLineCore);
        final BigDecimal monthlyRevenueCreditLine = this.getMonthlyRevenueCreditLine(creditLineCore);

        return monthlyRevenueCreditLine.compareTo(cashBalanceCreditLine) > 0 ? monthlyRevenueCreditLine : cashBalanceCreditLine;
    }
}
