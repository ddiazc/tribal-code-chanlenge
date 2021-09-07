package com.tribal.challenge.authorizer.repository.service;

import lombok.AllArgsConstructor;

import com.tribal.challenge.authorizer.domain.converter.CreditLineConverter;
import com.tribal.challenge.authorizer.domain.model.core.CreditLineCore;
import com.tribal.challenge.authorizer.domain.model.repository.CreditLineEntity;
import com.tribal.challenge.authorizer.domain.service.CreditLineRepositoryService;
import com.tribal.challenge.authorizer.repository.CreditLineRepository;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreditLineRepositoryServiceImpl implements CreditLineRepositoryService {

    private CreditLineRepository creditLineRepository;
    private CreditLineConverter creditLineConverter;

    @Override
    public CreditLineCore findById(String taxId) {
        return creditLineConverter.convertToCore(creditLineRepository.findById(taxId));
    }

    @Override
    public CreditLineCore save(CreditLineCore creditLineCore) {
        final CreditLineEntity creditLineEntity = creditLineConverter.convertToEntity(creditLineCore);
        return creditLineConverter.convertToCore(creditLineRepository.save(creditLineEntity));
    }
}
