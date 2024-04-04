package ru.reeste.manager.controller.budgetLine;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.reeste.manager.exception.BadRequestException;
import ru.reeste.manager.clients.BudgetLineRestClient;
import ru.reeste.manager.controller.payload.NewBudgetLineItemPayload;
import ru.reeste.manager.entity.BudgetLinesItem;

@Controller
@RequestMapping("budgets/budget-lines")
@RequiredArgsConstructor
public class BudgetLinesController {

    private final BudgetLineRestClient budgetLineRestClient;

    @GetMapping
    public String budgetLinesList(Model model) {
        model.addAttribute("budgetLines", budgetLineRestClient.findAllBudgetLines());
        return "budget-lines/budget-lines-list";
    }

    @GetMapping("create")
    public String getNewBudgetPage(Model model) {
        model.addAttribute("budgetLinesType", budgetLineRestClient.findAllBudgetLinesType());
        return "budget-lines/create";
    }

    @PostMapping("create")
    public String createNewBudgetLineItem(NewBudgetLineItemPayload payload,
                                          Model model) {
        try {
            BudgetLinesItem budgetLinesItem = budgetLineRestClient.createBudgetLineItem(payload.name(), payload.type());
            return "redirect:/budgets/budget-lines";
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            model.addAttribute("budgetLinesType", budgetLineRestClient.findAllBudgetLinesType());
            return "budget-lines/create";
        }
    }
}
