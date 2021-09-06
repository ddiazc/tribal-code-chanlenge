package com.tribal.challenge.authorizer.core.service;

import lombok.AllArgsConstructor;

import com.tribal.challenge.authorizer.domain.exception.TooManyRequestException;
import com.tribal.challenge.authorizer.domain.model.core.CreditLineCore;
import com.tribal.challenge.authorizer.domain.service.CreditLineRulesService;
import com.tribal.challenge.authorizer.repository.CreditLineApplicationRequestRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreditLineRulesServiceImpl implements CreditLineRulesService {

    private static final BigDecimal THREE = BigDecimal.valueOf(3);
    private static final BigDecimal FIVE = BigDecimal.valueOf(5);
    private static final int SCALE = 2;

    private CreditLineApplicationRequestRepository creditLineApplicationRequestRepository;

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

    @Override
    public void onOnceAcceptedCreditLineHandler(CreditLineCore creditLineCore) {
        final LocalDateTime nowMinusTwoMinutes = LocalDateTime.now()
                .minus(2, ChronoUnit.MINUTES);
        final int lastRequestCount = creditLineApplicationRequestRepository.countLastRequest(creditLineCore.getTaxId(), nowMinusTwoMinutes);
        if (lastRequestCount >= 3) {
            throw new TooManyRequestException("Too many request");
        }
    }

    @Override
    public void onOnceRejectedCreditLineHandler(CreditLineCore creditLineCore) {
        final int failedAttempts = creditLineCore.getFailedAttempts();
        if (failedAttempts >= 3) {
            throw new TooManyRequestException("A sales agent will contact you");
        }

        final LocalDateTime nowMinusThirtySeconds = LocalDateTime.now()
                .minus(30, ChronoUnit.SECONDS);
        final int lastRequestCount = creditLineApplicationRequestRepository.countLastRequest(creditLineCore.getTaxId(), nowMinusThirtySeconds);
        if (lastRequestCount >= 1) {
            throw new TooManyRequestException("Too many request");
        }
    }
}
