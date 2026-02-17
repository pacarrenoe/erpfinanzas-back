package cl.home.erpfinanzas.web.controller;

import cl.home.erpfinanzas.web.response.ApiResponse;
import cl.home.erpfinanzas.web.response.ResponseFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/ping")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> ping() {
        return ResponseEntity.ok(
                ResponseFactory.success("ADMIN_OK")
        );
    }
}
