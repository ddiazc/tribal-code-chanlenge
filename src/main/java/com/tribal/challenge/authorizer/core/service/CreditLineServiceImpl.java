package com.tribal.challenge.authorizer.core.service;

import lombok.AllArgsConstructor;

import com.tribal.challenge.authorizer.domain.converter.CreditLineConverter;
import com.tribal.challenge.authorizer.domain.exception.FoundingTypeNotRecognizedException;
import com.tribal.challenge.authorizer.domain.model.core.CreditLineCore;
import com.tribal.challenge.authorizer.domain.model.core.CreditLineResultCore;
import com.tribal.challenge.authorizer.domain.model.core.CreditLineStatus;
import com.tribal.challenge.authorizer.domain.model.repository.CreditLineEntity;
import com.tribal.challenge.authorizer.domain.service.CreditLineRulesService;
import com.tribal.challenge.authorizer.domain.service.CreditLineService;
import com.tribal.challenge.authorizer.repository.CreditLineRepository;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@AllArgsConstructor
public class CreditLineServiceImpl implements CreditLineService {

    private static final String SME_FOUNDING_TYPE = "SME";
    private static final String STARTUP_FOUNDING_TYPE = "STARTUP";

    private CreditLineRulesService creditLineRulesService;
    private CreditLineRepository creditLineRepository;
    private CreditLineConverter creditLineConverter;

    @Override
    public CreditLineResultCore evaluate(final CreditLineCore creditLine) {
        final BigDecimal requestedCreditLine = creditLine.getRequestedCreditLine();
        final BigDecimal recommendedLineCredit = this.getRecommendedCreditLine(creditLine);
        final CreditLineCore creditLineCoreFromDatabase = creditLineRepository.findById(creditLine.getTaxId())
                .map(creditLineEntity -> creditLineConverter.convertToCore(creditLineEntity))
                .orElseGet(() -> CreditLineCore.builder().build());

        if (StringUtils.hasText(creditLineCoreFromDatabase.getTaxId())
                && CreditLineStatus.ACCEPTED.toString().equals(creditLineCoreFromDatabase.getStatus())) {
            return CreditLineResultCore.builder()
                    .status(creditLineCoreFromDatabase.getStatus())
                    .creditLineAccepted(creditLineCoreFromDatabase.getRequestedCreditLine())
                    .build();
        }

        final CreditLineStatus creditLineStatus = requestedCreditLine.compareTo(recommendedLineCredit) < 0 ?
                CreditLineStatus.ACCEPTED : CreditLineStatus.REJECTED;

        final CreditLineCore creditLineCoreToSave = this.buildCreditLineWithStatus(creditLine,
                creditLineCoreFromDatabase,
                creditLineStatus);
        creditLineRepository.save(creditLineConverter.convertToEntity(creditLineCoreToSave));

        return CreditLineResultCore.builder()
                .status(creditLineStatus.toString())
                .creditLineAccepted(CreditLineStatus.ACCEPTED.equals(creditLineStatus) ? requestedCreditLine : null)
                .build();
    }

    private CreditLineCore buildCreditLineWithStatus(CreditLineCore creditLineCore,
                                                     CreditLineCore creditLineCoreFromDatabase,
                                                     CreditLineStatus status) {
        int failedAttempts = creditLineCoreFromDatabase.getFailedAttempts();
        if (CreditLineStatus.REJECTED.equals(status)) {
            failedAttempts++;
        }

        return CreditLineCore.builder()
                .taxId(creditLineCore.getTaxId())
                .foundingType(creditLineCore.getFoundingType())
                .cashBalance(creditLineCore.getCashBalance())
                .monthlyRevenue(creditLineCore.getMonthlyRevenue())
                .requestedCreditLine(creditLineCore.getRequestedCreditLine())
                .requestedDate(creditLineCore.getRequestedDate())
                .status(status.toString())
                .failedAttempts(failedAttempts)
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
