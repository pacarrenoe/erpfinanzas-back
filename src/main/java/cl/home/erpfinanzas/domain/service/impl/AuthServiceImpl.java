package cl.home.erpfinanzas.domain.service.impl;

import cl.home.erpfinanzas.application.dto.AuthTokensResponse;
import cl.home.erpfinanzas.domain.repository.AppUserRepository;
import cl.home.erpfinanzas.domain.repository.RefreshTokenRepository;
import cl.home.erpfinanzas.domain.service.AuthService;
import cl.home.erpfinanzas.infrastructure.persistence.entity.AppUserEntity;
import cl.home.erpfinanzas.infrastructure.persistence.entity.RefreshTokenEntity;
import cl.home.erpfinanzas.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final AppUserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(AppUserRepository userRepository,
                           RefreshTokenRepository refreshTokenRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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
        user.setRole("USER");
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    public AuthTokensResponse login(String email, String rawPassword) {

        AppUserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken = jwtService.generateAccessToken(user);

        String refreshToken = UUID.randomUUID().toString();

        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setId(UUID.randomUUID());
        entity.setToken(refreshToken);
        entity.setUser(user);
        entity.setExpiresAt(LocalDateTime.now().plusDays(7));
        entity.setRevoked(false);
        entity.setCreatedAt(LocalDateTime.now());

        refreshTokenRepository.save(entity);

        return new AuthTokensResponse(accessToken, refreshToken);
    }

    @Override
    public AuthTokensResponse refresh(String refreshToken) {

        RefreshTokenEntity stored = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (stored.isRevoked()) {
            throw new RuntimeException("Refresh token revoked");
        }

        if (stored.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        AppUserEntity user = stored.getUser();

        String newAccessToken = jwtService.generateAccessToken(user);

        return new AuthTokensResponse(newAccessToken, refreshToken);
    }
}
