package com.tribal.challenge.authorizer.repository;

import com.tribal.challenge.authorizer.domain.model.repository.CreditLineApplicationRequestEntity;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CreditLineApplicationRequestRepository extends JpaRepository<CreditLineApplicationRequestEntity, Integer> {

    @Query(value = "SELECT count(*)" +
            " FROM credit_line_request" +
            " WHERE created_at >= :dateTime" +
            " AND tax_id = :taxId",
            nativeQuery = true)
    int countLastRequest(@Param("taxId") String taxId, @Param("dateTime") LocalDateTime dateTime);
}
