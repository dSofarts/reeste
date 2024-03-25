package ru.reeste.app.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.reeste.app.controller.clients.BadRequestException;
import ru.reeste.app.controller.clients.BudgetRestClient;
import ru.reeste.app.controller.payload.UpdateBudgetPayload;
import ru.reeste.app.entity.Budget;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("budgets/{budgetId:\\d+}")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetRestClient budgetRestClient;

    @ModelAttribute("budget")
    public Budget budget(@PathVariable("budgetId") int budgetId) {
        return budgetRestClient.findBudget(budgetId).orElseThrow(() -> new NoSuchElementException("Такой бюджет не найдет, скорее всего он удален"));
    }

    @GetMapping
    public String getBudget() {
        return "budget/budget";
    }

    @GetMapping("edit")
    public String editBudget() {
        return "budget/editBudget";
    }

    @PostMapping("edit")
    public String updateBudget(@ModelAttribute(value = "budget", binding = false) Budget budget,
                               UpdateBudgetPayload payload,
                               Model model) {
        try {
            budgetRestClient.updateBudget(budget.id(), payload.name(), payload.year(), payload.quarter());
            return "redirect:/budgets/%d".formatted(budget.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "budget/editBudget";
        }
    }

    @PostMapping("delete")
    public String deleteBudget(@ModelAttribute("budget") Budget budget) {
        budgetRestClient.deleteBudget(budget.id());
        return "redirect:/budgets";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handlerNoSuchElementException(NoSuchElementException exception, Model model,
                                                HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        model.addAttribute("error", exception.getMessage());
        return "errors/404";
    }
}
