package cl.home.erpfinanzas.domain.service;

import cl.home.erpfinanzas.infrastructure.persistence.entity.IncomeEntity;

import java.util.UUID;

public interface IncomeService {
    IncomeEntity createIncome(UUID userId, IncomeEntity income);
}
