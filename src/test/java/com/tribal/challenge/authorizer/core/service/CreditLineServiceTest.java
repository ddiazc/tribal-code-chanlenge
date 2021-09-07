package com.tribal.challenge.authorizer.core.service;

import static com.tribal.challenge.authorizer.domain.model.core.CreditLineStatus.ACCEPTED;
import static com.tribal.challenge.authorizer.domain.model.core.CreditLineStatus.REJECTED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.tribal.challenge.authorizer.domain.model.core.CreditLineCore;
import com.tribal.challenge.authorizer.domain.model.core.CreditLineResultCore;
import com.tribal.challenge.authorizer.domain.service.CreditLineApplicationRequestRepositoryService;
import com.tribal.challenge.authorizer.domain.service.CreditLineRepositoryService;
import com.tribal.challenge.authorizer.domain.service.CreditLineRulesService;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {CreditLineServiceImpl.class})
public class CreditLineServiceTest {

    @MockBean
    private CreditLineRulesService creditLineRulesService;
    @MockBean
    private CreditLineRepositoryService creditLineRepositoryService;
    @MockBean
    private CreditLineApplicationRequestRepositoryService creditLineApplicationRequestRepositoryService;

    private CreditLineServiceImpl creditLineService;

    @BeforeEach
    public void init() {
        creditLineService = new CreditLineServiceImpl(creditLineRulesService, creditLineRepositoryService, creditLineApplicationRequestRepositoryService);
    }

    @Test
    public void shouldEvaluateAndAcceptSmeFoundingTypeCreditLineApplication_recommendedCreditLineGreaterThanRequestedCreditLine() {
        CreditLineCore creditLineCore = CreditLineCore.builder()
                .foundingType("SME")
                .requestedCreditLine(BigDecimal.ONE)
                .build();

        given(creditLineRulesService.getMonthlyRevenueCreditLine(any(CreditLineCore.class)))
                .willReturn(BigDecimal.valueOf(2));
        given(creditLineRepositoryService.findById(any())).willReturn(CreditLineCore.builder().build());

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
        given(creditLineRepositoryService.findById(any())).willReturn(CreditLineCore.builder().build());

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
        given(creditLineRepositoryService.findById(any())).willReturn(CreditLineCore.builder().build());

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
        given(creditLineRepositoryService.findById(any())).willReturn(CreditLineCore.builder().build());

        CreditLineResultCore expected = CreditLineResultCore.builder()
                .status(REJECTED.toString())
                .build();

        assertEquals(expected, creditLineService.evaluate(creditLineCore));
    }

    @Test
    public void shouldGetApprovedCreditLineFromPreviousAuthorization() {
        CreditLineCore creditLineCore = CreditLineCore.builder()
                .taxId("123")
                .foundingType("SME")
                .requestedCreditLine(BigDecimal.ONE)
                .build();
        CreditLineCore creditLineEntity = CreditLineCore.builder()
                .taxId(creditLineCore.getTaxId())
                .status(ACCEPTED.toString())
                .requestedCreditLine(BigDecimal.TEN)
                .build();

        given(creditLineRepositoryService.findById(eq(creditLineCore.getTaxId())))
                .willReturn(creditLineEntity);


        CreditLineResultCore expected = CreditLineResultCore.builder()
                .creditLineAccepted(BigDecimal.TEN)
                .status(ACCEPTED.toString())
                .build();

        assertEquals(expected, creditLineService.evaluate(creditLineCore));
        verify(creditLineRepositoryService, never()).save(any(CreditLineCore.class));
    }

    @Test
    public void shouldSaveRequestedCreditLineWithStatus() {
        CreditLineCore creditLineCore = CreditLineCore.builder()
                .taxId("123")
                .foundingType("SME")
                .requestedCreditLine(BigDecimal.ONE)
                .build();

        given(creditLineRulesService.getMonthlyRevenueCreditLine(any(CreditLineCore.class)))
                .willReturn(BigDecimal.valueOf(2));
        given(creditLineRepositoryService.findById(eq(creditLineCore.getTaxId())))
                .willReturn(CreditLineCore.builder().build());

        CreditLineResultCore expected = CreditLineResultCore.builder()
                .creditLineAccepted(creditLineCore.getRequestedCreditLine())
                .status(ACCEPTED.toString())
                .build();
        assertEquals(expected, creditLineService.evaluate(creditLineCore));
        verify(creditLineRepositoryService, times(1)).findById(any(String.class));
        verify(creditLineRepositoryService, times(1)).save(any(CreditLineCore.class));
    }

    @Test
    public void shouldIncrementFailsOnRejected() {
        CreditLineCore creditLineCore = CreditLineCore.builder()
                .taxId("123")
                .foundingType("SME")
                .requestedCreditLine(BigDecimal.TEN)
                .build();

        CreditLineCore creditLineCoreFromDB = CreditLineCore.builder()
                .taxId(creditLineCore.getTaxId())
                .status(REJECTED.toString())
                .requestedCreditLine(BigDecimal.TEN)
                .failedAttempts(2)
                .build();

        given(creditLineRulesService.getMonthlyRevenueCreditLine(any(CreditLineCore.class)))
                .willReturn(BigDecimal.valueOf(2));
        given(creditLineRepositoryService.findById(eq(creditLineCore.getTaxId())))
                .willReturn(creditLineCoreFromDB);

        creditLineService.evaluate(creditLineCore);

        ArgumentCaptor<CreditLineCore> creditLineCaptor = ArgumentCaptor.forClass(CreditLineCore.class);
        verify(creditLineRepositoryService).save(creditLineCaptor.capture());
        assertEquals(3, creditLineCaptor.getValue().getFailedAttempts());
    }
}
