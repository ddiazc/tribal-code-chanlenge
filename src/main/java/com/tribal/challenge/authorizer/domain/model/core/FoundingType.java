package com.tribal.challenge.authorizer.domain.model.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FoundingType {
    SME("SME"),STARTUP("STARTUP");
    private String name;
}
