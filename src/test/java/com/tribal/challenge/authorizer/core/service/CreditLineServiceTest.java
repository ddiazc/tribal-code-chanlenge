package com.tribal.challenge.authorizer.core.service;

import static com.tribal.challenge.authorizer.domain.model.core.CreditLineStatus.ACCEPTED;
import static com.tribal.challenge.authorizer.domain.model.core.CreditLineStatus.REJECTED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.tribal.challenge.authorizer.domain.model.core.CreditLineCore;
import com.tribal.challenge.authorizer.domain.model.core.CreditLineResultCore;
import com.tribal.challenge.authorizer.domain.service.CreditLineRulesService;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {CreditLineServiceImpl.class})
public class CreditLineServiceTest {

    @MockBean
    private CreditLineRulesService creditLineRulesService;

    private CreditLineServiceImpl creditLineService;

    @BeforeEach
    public void init() {
        creditLineService = new CreditLineServiceImpl(creditLineRulesService);
    }

    @Test
    public void shouldEvaluateAndAcceptSmeFoundingTypeCreditLineApplication_recommendedCreditLineGreaterThanRequestedCreditLine() {
        CreditLineCore creditLineCore = CreditLineCore.builder()
                .foundingType("SME")
                .requestedCreditLine(BigDecimal.ONE)
                .build();
        given(creditLineRulesService.getMonthlyRevenueCreditLine(any(CreditLineCore.class)))
                .willReturn(BigDecimal.valueOf(2));

        CreditLineResultCore expected = CreditLineResultCore.builder()
                .creditLineAccepted(creditLineCore.getRequestedCreditLine())
                .status(ACCEPTED.toString())
                .build();

        assertEquals(expected, creditLineService.evaluate(creditLineCore));
    }

    @Test
    public void shouldEvaluateAndRejectSmeFoundingTypeCreditLineApplication_recommendedCreditLineNotGreaterThanRequestedCreditLine() {
        CreditLineCore creditLineCore = CreditLineCore.builder()
                .foundingType("SME")
                .requestedCreditLine(BigDecimal.ONE)
                .build();

        given(creditLineRulesService.getMonthlyRevenueCreditLine(any(CreditLineCore.class)))
                .willReturn(BigDecimal.ONE);

        CreditLineResultCore expected = CreditLineResultCore.builder()
                .status(REJECTED.toString())
                .build();

        assertEquals(expected, creditLineService.evaluate(creditLineCore));
    }

    @Test
    public void shouldEvaluateAndAcceptStartupFoundingTypeCreditLineApplication_recommendedCreditLineGreaterThanRequestedCreditLine() {
        CreditLineCore creditLineCore = CreditLineCore.builder()
                .foundingType("startup")
                .requestedCreditLine(BigDecimal.ONE)
                .build();

        given(creditLineRulesService.getMaxRecommendedCreditLine(any(CreditLineCore.class)))
                .willReturn(BigDecimal.valueOf(2));

        CreditLineResultCore expected = CreditLineResultCore.builder()
                .creditLineAccepted(creditLineCore.getRequestedCreditLine())
                .status(ACCEPTED.toString())
                .build();

        assertEquals(expected, creditLineService.evaluate(creditLineCore));
    }

    @Test
    public void shouldEvaluateAndRejectStartupFoundingTypeCreditLineApplication_recommendedCreditLineNotGreaterThanRequestedCreditLine() {
        CreditLineCore creditLineCore = CreditLineCore.builder()
                .foundingType("SME")
                .requestedCreditLine(BigDecimal.ONE)
                .build();

        given(creditLineRulesService.getMonthlyRevenueCreditLine(any(CreditLineCore.class)))
                .willReturn(BigDecimal.ONE);

        CreditLineResultCore expected = CreditLineResultCore.builder()
                .status(REJECTED.toString())
                .build();

        assertEquals(expected, creditLineService.evaluate(creditLineCore));
    }
}
