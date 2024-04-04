package ru.reeste.committee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import ru.reeste.committee.client.BudgetClient;

@Controller
@RequiredArgsConstructor
@RequestMapping("committee/budgets")
public class BudgetsController {

    private final BudgetClient budgetClient;

    @GetMapping("list")
    public Mono<String> getBudgetsListPage(Model model) {
        return budgetClient.findAllBudgets()
                .collectList()
                .doOnNext(budgets -> model.addAttribute("budgets", budgets))
                .thenReturn("budgets/list");
    }
}
