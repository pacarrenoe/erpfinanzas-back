package cl.home.erpfinanzas.web.controller;

import cl.home.erpfinanzas.application.dto.CreateIncomeRequest;
import cl.home.erpfinanzas.domain.service.IncomeService;
import cl.home.erpfinanzas.infrastructure.persistence.entity.IncomeEntity;
import cl.home.erpfinanzas.security.UserContext;
import cl.home.erpfinanzas.web.response.ApiResponse;
import cl.home.erpfinanzas.web.response.ResponseFactory;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    private final IncomeService incomeService;
    private final UserContext userContext;

    public IncomeController(IncomeService incomeService, UserContext userContext) {
        this.incomeService = incomeService;
        this.userContext = userContext;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(@Valid @RequestBody CreateIncomeRequest req) {

        IncomeEntity income = new IncomeEntity();
        income.setIncomeDate(req.getIncomeDate());
        income.setAmount(req.getAmount());
        income.setType(req.getType());
        income.setDescription(req.getDescription());
        income.setPeriodStart(req.isPeriodStart());

        IncomeEntity saved = incomeService.createIncome(userContext.requireUserId(), income);

        // respuesta mínima útil
        return ResponseEntity.ok(ResponseFactory.success(new IncomeCreatedData(saved.getId(), saved.getPeriodId())));
    }

    public static class IncomeCreatedData {
        private final Object incomeId;
        private final Object periodId;

        public IncomeCreatedData(Object incomeId, Object periodId) {
            this.incomeId = incomeId;
            this.periodId = periodId;
        }

        public Object getIncomeId() { return incomeId; }
        public Object getPeriodId() { return periodId; }
    }
}
