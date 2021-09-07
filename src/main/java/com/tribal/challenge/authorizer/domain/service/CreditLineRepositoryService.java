package com.tribal.challenge.authorizer.domain.service;

import com.tribal.challenge.authorizer.domain.model.core.CreditLineCore;

public interface CreditLineRepositoryService {

    CreditLineCore findById(String taxId);
    CreditLineCore save(CreditLineCore creditLineCore);
}
