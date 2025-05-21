package com.eventify.eventify.module.event.repository;

import com.eventify.eventify.module.event.model.entity.FinancialOfTheEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialOfTheEventRepository extends JpaRepository<FinancialOfTheEventEntity, Long> {
}
