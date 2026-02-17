package cl.home.erpfinanzas.domain.service.impl;

import cl.home.erpfinanzas.domain.service.IncomeService;
import cl.home.erpfinanzas.infrastructure.persistence.entity.FinancialPeriodEntity;
import cl.home.erpfinanzas.infrastructure.persistence.entity.IncomeEntity;
import cl.home.erpfinanzas.infrastructure.persistence.repository.JpaFinancialPeriodRepository;
import cl.home.erpfinanzas.infrastructure.persistence.repository.JpaIncomeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final JpaIncomeRepository incomeRepository;
    private final JpaFinancialPeriodRepository periodRepository;

    public IncomeServiceImpl(JpaIncomeRepository incomeRepository,
                             JpaFinancialPeriodRepository periodRepository) {
        this.incomeRepository = incomeRepository;
        this.periodRepository = periodRepository;
    }

    @Override
    @Transactional
    public IncomeEntity createIncome(UUID userId, IncomeEntity income) {

        income.setUserId(userId);
        income.setCreatedAt(LocalDateTime.now());

        if (income.isPeriodStart()) {
            // cerrar periodo abierto si existe
            periodRepository.findFirstByUserIdAndEndDateIsNullOrderByStartDateDesc(userId)
                    .ifPresent(open -> {
                        open.setEndDate(income.getIncomeDate().minusDays(1));
                        periodRepository.save(open);
                    });

            // crear nuevo periodo
            FinancialPeriodEntity p = new FinancialPeriodEntity();
            p.setUserId(userId);
            p.setStartDate(income.getIncomeDate());
            p.setEndDate(null);
            p.setCreatedAt(LocalDateTime.now());
            p = periodRepository.save(p);

            income.setPeriodId(p.getId());
            return incomeRepository.save(income);
        }

        // ingreso extra: asigna periodo por fecha
        FinancialPeriodEntity p = periodRepository.findPeriodForDate(userId, income.getIncomeDate())
                .orElseThrow(() -> new RuntimeException("Debe crear un período (registrar sueldo principal)"));

        income.setPeriodId(p.getId());
        return incomeRepository.save(income);
    }
}
