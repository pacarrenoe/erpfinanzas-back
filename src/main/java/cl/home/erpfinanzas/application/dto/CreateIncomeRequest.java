package cl.home.erpfinanzas.application.dto;

import cl.home.erpfinanzas.infrastructure.persistence.entity.IncomeEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateIncomeRequest {

    @NotNull
    private LocalDate incomeDate;

    @NotNull
    @PositiveOrZero
    private BigDecimal amount;

    @NotNull
    private IncomeEntity.IncomeType type;

    private String description;

    private boolean periodStart;

    public LocalDate getIncomeDate() { return incomeDate; }
    public void setIncomeDate(LocalDate incomeDate) { this.incomeDate = incomeDate; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public IncomeEntity.IncomeType getType() { return type; }
    public void setType(IncomeEntity.IncomeType type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isPeriodStart() { return periodStart; }
    public void setPeriodStart(boolean periodStart) { this.periodStart = periodStart; }
}
