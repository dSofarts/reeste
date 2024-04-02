package ru.reeste.budget.controller.budgetLine;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.reeste.budget.controller.payload.NewBudgetLineItemPayload;
import ru.reeste.budget.entity.BudgetLinesItem;
import ru.reeste.budget.entity.BudgetLinesType;
import ru.reeste.budget.service.BudgetLineService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/budget-lines")
public class BudgetLinesRestController {

    private final BudgetLineService budgetLineService;

    @GetMapping
    public Iterable<BudgetLinesItem> getAllBudgetLines() {
        return budgetLineService.findAllBudgetLinesItem();
    }

    @GetMapping("types")
    public Iterable<BudgetLinesType> getAllBudgetLinesTypes() {
        return budgetLineService.findAllBudgetLinesTypes();
    }

    @PostMapping
    public ResponseEntity<?> createNewBudgetLineItem(@Valid @RequestBody NewBudgetLineItemPayload payload,
                                                     BindingResult bindingResult,
                                                     UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            BudgetLinesItem budgetLinesItem = budgetLineService.createBudgetLineItem(payload.name(), payload.type());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/api/v1/budget-lines/{bidgetLineItemId}")
                            .build(Map.of("bidgetLineItemId", budgetLinesItem.getId())))
                    .body(budgetLinesItem);
        }
    }
}
