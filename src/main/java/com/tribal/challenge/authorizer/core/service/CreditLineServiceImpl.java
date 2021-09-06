package com.tribal.challenge.authorizer.core.service;

import lombok.AllArgsConstructor;

import com.tribal.challenge.authorizer.domain.exception.FoundingTypeNotRecognizedException;
import com.tribal.challenge.authorizer.domain.model.core.CreditLineCore;
import com.tribal.challenge.authorizer.domain.model.core.CreditLineResultCore;
import com.tribal.challenge.authorizer.domain.model.core.CreditLineStatus;
import com.tribal.challenge.authorizer.domain.service.CreditLineRulesService;
import com.tribal.challenge.authorizer.domain.service.CreditLineService;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreditLineServiceImpl implements CreditLineService {

    private static final String SME_FOUNDING_TYPE = "SME";
    private static final String STARTUP_FOUNDING_TYPE = "STARTUP";

    private CreditLineRulesService creditLineRulesService;

    @Override
    public CreditLineResultCore evaluate(final CreditLineCore creditLine) {
        final BigDecimal requestedCreditLine = creditLine.getRequestedCreditLine();
        final BigDecimal recommendedLineCredit = this.getRecommendedCreditLine(creditLine);

        final CreditLineStatus creditLineStatus = requestedCreditLine.compareTo(recommendedLineCredit) < 0 ?
                CreditLineStatus.ACCEPTED : CreditLineStatus.REJECTED;
        return CreditLineResultCore.builder()
                .status(creditLineStatus.toString())
                .creditLineAccepted(CreditLineStatus.ACCEPTED.equals(creditLineStatus) ? requestedCreditLine : null)
                .build();
    }

    @Override
    public BigDecimal getRecommendedCreditLine(CreditLineCore creditLine) {
        final String foundingType = creditLine.getFoundingType().toUpperCase();
        switch (foundingType) {
            case SME_FOUNDING_TYPE:
                return creditLineRulesService.getMonthlyRevenueCreditLine(creditLine);
            case STARTUP_FOUNDING_TYPE:
                return creditLineRulesService.getMaxRecommendedCreditLine(creditLine);
            default:
                throw new FoundingTypeNotRecognizedException(foundingType);
        }
    }
}
