package ru.reeste.budget.controller.budget;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.reeste.budget.controller.payload.UpdateBudgetPayload;
import ru.reeste.budget.entity.Budget;
import ru.reeste.budget.service.BudgetService;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/budgets/{budgetId:\\d+}")
public class BudgetRestController {

    private final BudgetService budgetService;

    @ModelAttribute("budget")
    public Budget budget(@PathVariable("budgetId") int budgetId) {
        return budgetService.findBudget(budgetId).orElseThrow(() ->
                new NoSuchElementException("Бюджет не найден"));
    }

    @GetMapping
    public Budget findBudget(@ModelAttribute("budget") Budget budget) {
        return budget;
    }

    @PatchMapping
    public ResponseEntity<?> updateBudget(@PathVariable("budgetId") int budgetId,
                                             @Valid @RequestBody UpdateBudgetPayload payload,
                                             BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            budgetService.updateBudget(budgetId, payload.name(), payload.year(), payload.quarter());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBudget(@PathVariable("budgetId") int budgetId) {
        budgetService.deleteBudget(budgetId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handlerNoSuchElementException(NoSuchElementException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                exception.getMessage()));
    }
}
