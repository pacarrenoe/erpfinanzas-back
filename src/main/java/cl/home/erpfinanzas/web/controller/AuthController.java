package cl.home.erpfinanzas.web.controller;

import cl.home.erpfinanzas.application.dto.LoginRequest;
import cl.home.erpfinanzas.application.dto.RegisterRequest;
import cl.home.erpfinanzas.domain.service.AuthService;
import cl.home.erpfinanzas.infrastructure.persistence.entity.AppUserEntity;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import cl.home.erpfinanzas.web.response.ResponseFactory;
import cl.home.erpfinanzas.web.response.ApiResponse;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService,
                          AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody RegisterRequest request) {

        AppUserEntity user = authService.register(
                request.getEmail(),
                request.getPassword(),
                request.getDisplayName()
        );

        return ResponseEntity.ok(
                ResponseFactory.success(user.getId())
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        return ResponseEntity.ok(
                ResponseFactory.success("Login exitoso")
        );
    }

}
