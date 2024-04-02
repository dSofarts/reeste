package ru.reeste.budget.controller.budgetLine;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.reeste.budget.entity.BudgetLinesItem;
import ru.reeste.budget.service.BudgetLineService;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/budget-lines/{budgetLineId:\\d+}")
public class BudgetLineRestController {

    private final BudgetLineService budgetLineService;

    @ModelAttribute("budgetLine")
    public BudgetLinesItem budgetLine(@PathVariable("budgetLineId") int budgetLineId) {
        return budgetLineService.findBudgetLine(budgetLineId).orElseThrow(() ->
                new NoSuchElementException("Статья не найдена"));
    }

    @GetMapping
    public BudgetLinesItem findBudgetLineItem(@ModelAttribute("budgetLine") BudgetLinesItem budgetLine) {
        return budgetLine;
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBudgetLineItem(@PathVariable("budgetLineId") int budgetLineId) {
        budgetLineService.deleteBudgetLineItem(budgetLineId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handlerNoSuchElementException(NoSuchElementException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                exception.getMessage()));
    }
}
