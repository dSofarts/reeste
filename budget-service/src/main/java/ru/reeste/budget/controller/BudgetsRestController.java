package ru.reeste.budget.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.reeste.budget.controller.payload.NewBudgetPayload;
import ru.reeste.budget.entity.Budget;
import ru.reeste.budget.service.BudgetService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/budgets")
public class BudgetsRestController {

    private final BudgetService budgetService;

    @GetMapping
    public List<Budget> getAllBudgets() {
        return budgetService.findAllBudgets();
    }

    @PostMapping
    public ResponseEntity<?> createBudget(@Valid @RequestBody NewBudgetPayload payload,
                                          BindingResult bindingResult,
                                          UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Budget budget = budgetService.createBudget(payload.name(), payload.year(), payload.quarter());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/api/v1/budgets/{budgetId}")
                            .build(Map.of("budgetId", budget.getId())))
                    .body(budget);
        }
    }
}
