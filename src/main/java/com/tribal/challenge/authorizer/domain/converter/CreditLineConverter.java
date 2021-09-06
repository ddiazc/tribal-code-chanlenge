package com.tribal.challenge.authorizer.domain.converter;



import com.tribal.challenge.authorizer.domain.model.api.CreditLineDTO;
import com.tribal.challenge.authorizer.domain.model.core.CreditLineCore;

import org.springframework.stereotype.Component;

@Component
public class CreditLineConverter {

    public CreditLineCore convertToCore(final CreditLineDTO creditLineDTO) {
        return CreditLineCore.builder()
                .taxId(creditLineDTO.getTaxId())
                .foundingType(creditLineDTO.getFoundingType())
                .cashBalance(creditLineDTO.getCashBalance())
                .monthlyRevenue(creditLineDTO.getMonthlyRevenue())
                .requestedCreditLine(creditLineDTO.getRequestedCreditLine())
                .requestedDate(creditLineDTO.getRequestedDate())
                .build();
    }

    public CreditLineDTO convertToApiDTO(final CreditLineCore creditLineCore) {
        return CreditLineDTO.builder()
                .taxId(creditLineCore.getTaxId())
                .foundingType(creditLineCore.getFoundingType())
                .cashBalance(creditLineCore.getCashBalance())
                .monthlyRevenue(creditLineCore.getMonthlyRevenue())
                .requestedCreditLine(creditLineCore.getRequestedCreditLine())
                .requestedDate(creditLineCore.getRequestedDate())
                .build();
    }
}
