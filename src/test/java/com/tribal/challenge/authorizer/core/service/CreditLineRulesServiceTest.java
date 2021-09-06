package com.tribal.challenge.authorizer.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.tribal.challenge.authorizer.domain.model.core.CreditLineCore;
import com.tribal.challenge.authorizer.repository.CreditLineApplicationRequestRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {CreditLineRulesServiceImpl.class})
public class CreditLineRulesServiceTest {

    @MockBean
    private CreditLineApplicationRequestRepository creditLineApplicationRequestRepository;

    private CreditLineRulesServiceImpl creditLineRulesServiceImpl;

    @BeforeEach
    public void init() {
        creditLineRulesServiceImpl = new CreditLineRulesServiceImpl(creditLineApplicationRequestRepository);
    }

    @Test
    public void getCashBalanceCreditLineShouldGetValueDivideByThree() {
        CreditLineCore creditLineCore = CreditLineCore.builder()
                .cashBalance(BigDecimal.valueOf(3))
                .build();
        BigDecimal expectedResult = BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_EVEN);

        assertEquals(expectedResult, creditLineRulesServiceImpl.getCashBalanceCreditLine(creditLineCore));
    }

    @Test
    public void getMonthlyRevenueCreditLineDividedByFive() {
        CreditLineCore creditLineCore = CreditLineCore.builder()
                .monthlyRevenue(BigDecimal.valueOf(5))
                .build();
        BigDecimal expectedResult = BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_EVEN);

        assertEquals(expectedResult, creditLineRulesServiceImpl.getMonthlyRevenueCreditLine(creditLineCore));
    }

    @Test
    public void getMaxRecommendedCreditLineWhenCashBalanceRuleIsBigger() {
        CreditLineCore creditLineCore = CreditLineCore.builder()
                .cashBalance(BigDecimal.valueOf(9))
                .monthlyRevenue(BigDecimal.valueOf(5))
                .build();
        BigDecimal expectedResult = BigDecimal.valueOf(3).setScale(2, RoundingMode.HALF_EVEN);

        assertEquals(expectedResult, creditLineRulesServiceImpl.getMaxRecommendedCreditLine(creditLineCore));
    }

    @Test
    public void getMaxRecommendedCreditLineWhenMonthlyRevenueRuleIsBigger() {
        CreditLineCore creditLineCore = CreditLineCore.builder()
                .cashBalance(BigDecimal.valueOf(3))
                .monthlyRevenue(BigDecimal.valueOf(10))
                .build();
        BigDecimal expectedResult = BigDecimal.valueOf(2).setScale(2, RoundingMode.HALF_EVEN);

        assertEquals(expectedResult, creditLineRulesServiceImpl.getMaxRecommendedCreditLine(creditLineCore));
    }
}
