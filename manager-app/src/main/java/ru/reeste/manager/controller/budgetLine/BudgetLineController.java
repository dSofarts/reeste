package ru.reeste.manager.controller.budgetLine;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.reeste.manager.clients.BudgetLineRestClient;
import ru.reeste.manager.entity.BudgetLinesItem;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("budgets/budget-lines/{budgetLineId:\\d+}")
public class BudgetLineController {

    private final BudgetLineRestClient budgetLineRestClient;

    @ModelAttribute(name = "budgetLine", binding = false)
    public BudgetLinesItem budgetLinesItem(@PathVariable("budgetLineId") int budgetLineId) {
        return budgetLineRestClient.findBudgetLinesItem(budgetLineId)
                .orElseThrow(() -> new NoSuchElementException("Данная статья бюджета уже удалена"));
    }

    @PostMapping("delete")
    public String deleteBudget(@ModelAttribute("budgetLine") BudgetLinesItem budgetLinesItem) {
        budgetLineRestClient.deleteBudgetLineItem(budgetLinesItem.id());
        return "redirect:/budgets/budget-lines";
    }
}
