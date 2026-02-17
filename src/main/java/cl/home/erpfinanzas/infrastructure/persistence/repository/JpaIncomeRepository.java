package cl.home.erpfinanzas.infrastructure.persistence.repository;

import cl.home.erpfinanzas.infrastructure.persistence.entity.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaIncomeRepository extends JpaRepository<IncomeEntity, UUID> {
}
