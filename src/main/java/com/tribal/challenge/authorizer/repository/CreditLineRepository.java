package com.tribal.challenge.authorizer.repository;

import com.tribal.challenge.authorizer.domain.model.repository.CreditLineEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditLineRepository extends JpaRepository<CreditLineEntity,String> {
}
