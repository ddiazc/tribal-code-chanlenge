package com.tribal.challenge.authorizer.api.controller;

import lombok.AllArgsConstructor;

import com.tribal.challenge.authorizer.domain.converter.CreditLineConverter;
import com.tribal.challenge.authorizer.domain.converter.CreditLineResultConverter;
import com.tribal.challenge.authorizer.domain.model.api.CreditLineDTO;
import com.tribal.challenge.authorizer.domain.model.api.CreditLineResultDTO;
import com.tribal.challenge.authorizer.domain.model.core.CreditLineCore;
import com.tribal.challenge.authorizer.domain.service.CreditLineService;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/credit-line")
public class CreditLineController {

    private CreditLineService creditLineService;
    private CreditLineResultConverter creditLineResultConverter;
    private CreditLineConverter creditLineConverter;

    @PostMapping("/request")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CreditLineResultDTO request(@Valid @RequestBody final CreditLineDTO creditLineDTO) {
        final CreditLineCore creditLineCore = creditLineConverter.convertToCore(creditLineDTO);
        creditLineService.preHandle(creditLineCore);
        return creditLineResultConverter.convertToApiDTO(creditLineService.evaluate(creditLineCore));
    }
}
