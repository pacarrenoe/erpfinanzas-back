package cl.home.erpfinanzas.web.controller;

import cl.home.erpfinanzas.infrastructure.persistence.entity.FinancialPeriodEntity;
import cl.home.erpfinanzas.infrastructure.persistence.repository.JpaFinancialPeriodRepository;
import cl.home.erpfinanzas.security.UserContext;
import cl.home.erpfinanzas.web.response.ApiResponse;
import cl.home.erpfinanzas.web.response.ResponseFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/periods")
public class PeriodController {

    private final JpaFinancialPeriodRepository periodRepository;
    private final UserContext userContext;

    public PeriodController(JpaFinancialPeriodRepository periodRepository, UserContext userContext) {
        this.periodRepository = periodRepository;
        this.userContext = userContext;
    }

    @GetMapping("/current")
    public ResponseEntity<ApiResponse<?>> current() {
        var userId = userContext.requireUserId();
        FinancialPeriodEntity p = periodRepository.findFirstByUserIdAndEndDateIsNullOrderByStartDateDesc(userId)
                .orElseGet(() -> periodRepository.findTop12ByUserIdOrderByStartDateDesc(userId).stream().findFirst().orElse(null));
        return ResponseEntity.ok(ResponseFactory.success(p));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> list() {
        var userId = userContext.requireUserId();
        List<FinancialPeriodEntity> ps = periodRepository.findTop12ByUserIdOrderByStartDateDesc(userId);
        return ResponseEntity.ok(ResponseFactory.success(ps));
    }
}
