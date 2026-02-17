package cl.home.erpfinanzas.infrastructure.persistence.repository;

import cl.home.erpfinanzas.infrastructure.persistence.entity.FinancialPeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaFinancialPeriodRepository extends JpaRepository<FinancialPeriodEntity, UUID> {

    Optional<FinancialPeriodEntity> findFirstByUserIdAndEndDateIsNullOrderByStartDateDesc(UUID userId);

    @Query("""
        select p from FinancialPeriodEntity p
        where p.userId = :userId
          and p.startDate <= :date
          and (p.endDate is null or p.endDate >= :date)
        order by p.startDate desc
        """)
    Optional<FinancialPeriodEntity> findPeriodForDate(UUID userId, LocalDate date);

    List<FinancialPeriodEntity> findTop12ByUserIdOrderByStartDateDesc(UUID userId);
}
