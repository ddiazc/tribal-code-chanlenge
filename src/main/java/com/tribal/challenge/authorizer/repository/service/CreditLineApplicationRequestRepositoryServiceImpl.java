package com.tribal.challenge.authorizer.repository.service;

import lombok.AllArgsConstructor;

import com.tribal.challenge.authorizer.domain.model.core.CreditLineCore;
import com.tribal.challenge.authorizer.domain.model.repository.CreditLineApplicationRequestEntity;
import com.tribal.challenge.authorizer.domain.service.CreditLineApplicationRequestRepositoryService;
import com.tribal.challenge.authorizer.repository.CreditLineApplicationRequestRepository;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreditLineApplicationRequestRepositoryServiceImpl implements CreditLineApplicationRequestRepositoryService {

    private CreditLineApplicationRequestRepository creditLineApplicationRequestRepository;

    @Override
    public void save(CreditLineCore creditLineCore) {
        creditLineApplicationRequestRepository.save(buildCreditLineApplicationRequestEntity(creditLineCore));
    }

    private CreditLineApplicationRequestEntity buildCreditLineApplicationRequestEntity(CreditLineCore creditLineCore) {
        return CreditLineApplicationRequestEntity.builder()
                .taxId(creditLineCore.getTaxId())
                .build();
    }

    @Override
    public int countLastRequest(String taxId, LocalDateTime localDateTime) {
        return creditLineApplicationRequestRepository.countLastRequest(taxId, localDateTime);
    }
}
