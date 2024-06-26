package ru.reeste.manager.controller.budget;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.reeste.manager.exception.BadRequestException;
import ru.reeste.manager.clients.BudgetRestClient;
import ru.reeste.manager.controller.payload.NewBudgetPayload;
import ru.reeste.manager.entity.Budget;

@Controller
@RequestMapping("budgets")
@RequiredArgsConstructor
public class BudgetsController {

    private final BudgetRestClient budgetRestClient;

    @GetMapping
    public String budgetList(Model model) {
        model.addAttribute("budgets", budgetRestClient.findAllBudgets());
        return "budget/budgets";
    }

    @GetMapping("create")
    public String getNewBudgetPage() {
        return "budget/createBudget";
    }

    @PostMapping("create")
    public String createBudget(NewBudgetPayload payload,
                               Model model) {
        try {
            Budget budget = budgetRestClient.createBudget(payload.name(), payload.year(), payload.quarter());
            return "redirect:/budgets/%d".formatted(budget.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "budget/createBudget";
        }
    }
}
