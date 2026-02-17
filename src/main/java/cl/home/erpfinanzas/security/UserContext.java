package cl.home.erpfinanzas.security;

import cl.home.erpfinanzas.domain.repository.AppUserRepository;
import cl.home.erpfinanzas.infrastructure.persistence.entity.AppUserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserContext {

    private final AppUserRepository appUserRepository;

    public UserContext(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public UUID requireUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new RuntimeException("Unauthorized");
        }
        String email = auth.getName();

        AppUserEntity user = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Unauthorized"));

        return user.getId();
    }
}
