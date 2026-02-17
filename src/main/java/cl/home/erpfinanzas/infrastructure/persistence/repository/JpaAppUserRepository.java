package cl.home.erpfinanzas.infrastructure.persistence.repository;

import cl.home.erpfinanzas.infrastructure.persistence.entity.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaAppUserRepository extends JpaRepository<AppUserEntity, UUID> {

    Optional<AppUserEntity> findByEmail(String email);
}
