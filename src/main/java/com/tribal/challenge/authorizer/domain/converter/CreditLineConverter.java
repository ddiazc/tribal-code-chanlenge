package com.tribal.challenge.authorizer.domain.converter;



import com.tribal.challenge.authorizer.domain.model.api.CreditLineDTO;
import com.tribal.challenge.authorizer.domain.model.core.CreditLineCore;
import com.tribal.challenge.authorizer.domain.model.repository.CreditLineEntity;

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

    public CreditLineEntity convertToEntity(final CreditLineCore creditLineCore) {
        return CreditLineEntity.builder()
                .taxId(creditLineCore.getTaxId())
                .foundingType(creditLineCore.getFoundingType())
                .cashBalance(creditLineCore.getCashBalance())
                .monthlyRevenue(creditLineCore.getMonthlyRevenue())
                .requestedCreditLine(creditLineCore.getRequestedCreditLine())
                .requestedDate(creditLineCore.getRequestedDate())
                .status(creditLineCore.getStatus())
                .failedAttempts(creditLineCore.getFailedAttempts())
                .build();
    }

    public CreditLineCore convertToCore(final CreditLineEntity creditLineEntity) {
        return CreditLineCore.builder()
                .taxId(creditLineEntity.getTaxId())
                .foundingType(creditLineEntity.getFoundingType())
                .cashBalance(creditLineEntity.getCashBalance())
                .monthlyRevenue(creditLineEntity.getMonthlyRevenue())
                .requestedCreditLine(creditLineEntity.getRequestedCreditLine())
                .requestedDate(creditLineEntity.getRequestedDate())
                .status(creditLineEntity.getStatus())
                .failedAttempts(creditLineEntity.getFailedAttempts())
                .build();
    }
}
