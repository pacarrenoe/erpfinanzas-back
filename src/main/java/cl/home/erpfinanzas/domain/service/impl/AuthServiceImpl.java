package cl.home.erpfinanzas.domain.service.impl;

import cl.home.erpfinanzas.domain.repository.AppUserRepository;
import cl.home.erpfinanzas.domain.service.AuthService;
import cl.home.erpfinanzas.infrastructure.persistence.entity.AppUserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AppUserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUserEntity register(String email, String rawPassword, String displayName) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        AppUserEntity user = new AppUserEntity();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        user.setDisplayName(displayName);
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }
}
