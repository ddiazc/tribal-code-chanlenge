package com.tribal.challenge.authorizer.domain.converter;

import com.tribal.challenge.authorizer.domain.model.api.CreditLineResultDTO;
import com.tribal.challenge.authorizer.domain.model.core.CreditLineResultCore;

import org.springframework.stereotype.Component;

@Component
public class CreditLineResultConverter {

    public CreditLineResultCore convertToCore(final CreditLineResultDTO creditLineResultDTO) {
        return CreditLineResultCore.builder()
                .status(creditLineResultDTO.getStatus())
                .creditLineAccepted(creditLineResultDTO.getCreditLineAccepted())
                .build();
    }

    public CreditLineResultDTO convertToApiDTO(final CreditLineResultCore creditLineResultCore) {
        return CreditLineResultDTO.builder()
                .status(creditLineResultCore.getStatus())
                .creditLineAccepted(creditLineResultCore.getCreditLineAccepted())
                .build();
    }
}
