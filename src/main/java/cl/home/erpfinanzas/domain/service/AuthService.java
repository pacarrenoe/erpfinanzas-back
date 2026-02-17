package cl.home.erpfinanzas.domain.service;

import cl.home.erpfinanzas.infrastructure.persistence.entity.AppUserEntity;

public interface AuthService {

    AppUserEntity register(String email, String rawPassword, String displayName);

}
