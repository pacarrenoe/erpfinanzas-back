package cl.home.erpfinanzas.infrastructure.persistence.repository;

import cl.home.erpfinanzas.domain.repository.AppUserRepository;
import cl.home.erpfinanzas.infrastructure.persistence.entity.AppUserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class AppUserRepositoryImpl implements AppUserRepository {

    private final JpaAppUserRepository jpaRepository;

    public AppUserRepositoryImpl(JpaAppUserRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public AppUserEntity save(AppUserEntity user) {
        return jpaRepository.save(user);
    }

    @Override
    public Optional<AppUserEntity> findByEmail(String email) {
        return jpaRepository.findByEmail(email);
    }

    @Override
    public Optional<AppUserEntity> findById(UUID id) {
        return jpaRepository.findById(id);
    }
}
