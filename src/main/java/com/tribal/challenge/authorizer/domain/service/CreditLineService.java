package com.tribal.challenge.authorizer.domain.service;

import com.tribal.challenge.authorizer.domain.model.core.CreditLineCore;
import com.tribal.challenge.authorizer.domain.model.core.CreditLineResultCore;

import java.math.BigDecimal;

public interface CreditLineService {
    void preHandle(CreditLineCore creditLine);

    CreditLineResultCore evaluate(CreditLineCore creditLine);

    BigDecimal getRecommendedCreditLine(CreditLineCore creditLine);
}
