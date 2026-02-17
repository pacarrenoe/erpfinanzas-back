package cl.home.erpfinanzas.web.controller;

import cl.home.erpfinanzas.application.dto.AuthTokensResponse;
import cl.home.erpfinanzas.application.dto.LoginRequest;
import cl.home.erpfinanzas.application.dto.RegisterRequest;
import cl.home.erpfinanzas.domain.repository.AppUserRepository;
import cl.home.erpfinanzas.domain.service.AuthService;
import cl.home.erpfinanzas.infrastructure.persistence.entity.AppUserEntity;
import cl.home.erpfinanzas.web.response.ApiResponse;
import cl.home.erpfinanzas.web.response.ResponseFactory;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AppUserRepository appUserRepository;

    public AuthController(AuthService authService, AppUserRepository appUserRepository) {
        this.authService = authService;
        this.appUserRepository = appUserRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody RegisterRequest request) {
        AppUserEntity user = authService.register(
                request.getEmail(),
                request.getPassword(),
                request.getDisplayName()
        );
        return ResponseEntity.ok(ResponseFactory.success(Map.of(
                "userId", user.getId(),
                "email", user.getEmail(),
                "displayName", user.getDisplayName(),
                "role", user.getRole()
        )));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginRequest request) {
        AuthTokensResponse tokens = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(ResponseFactory.success(tokens));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<?>> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        AuthTokensResponse tokens = authService.refresh(refreshToken);
        return ResponseEntity.ok(ResponseFactory.success(tokens));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> me(Authentication authentication) {
        String email = authentication.getName();

        AppUserEntity user = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(ResponseFactory.success(Map.of(
                "userId", user.getId(),
                "email", user.getEmail(),
                "displayName", user.getDisplayName(),
                "role", user.getRole(),
                "createdAt", user.getCreatedAt()
        )));
    }
}
