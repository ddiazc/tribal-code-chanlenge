package com.tribal.challenge.authorizer.domain.service;

import com.tribal.challenge.authorizer.domain.model.core.CreditLineCore;

import java.time.LocalDateTime;

public interface CreditLineApplicationRequestRepositoryService {

    void save(CreditLineCore creditLineCore);
    int countLastRequest(String taxId, LocalDateTime localDateTime);
}
