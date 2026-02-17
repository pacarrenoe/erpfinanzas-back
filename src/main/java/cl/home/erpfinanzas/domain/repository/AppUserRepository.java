package cl.home.erpfinanzas.domain.repository;

import cl.home.erpfinanzas.infrastructure.persistence.entity.AppUserEntity;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository {

    AppUserEntity save(AppUserEntity user);

    Optional<AppUserEntity> findByEmail(String email);

    Optional<AppUserEntity> findById(UUID id);
}
