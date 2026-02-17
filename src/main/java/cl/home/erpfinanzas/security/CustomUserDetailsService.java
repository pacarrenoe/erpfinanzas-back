package cl.home.erpfinanzas.security;

import cl.home.erpfinanzas.domain.repository.AppUserRepository;
import cl.home.erpfinanzas.infrastructure.persistence.entity.AppUserEntity;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository userRepository;

    public CustomUserDetailsService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        AppUserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(
                user.getEmail(),
                user.getPasswordHash(),
                Collections.emptyList()
        );
    }
}
