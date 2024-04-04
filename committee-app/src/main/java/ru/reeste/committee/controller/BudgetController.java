package ru.reeste.committee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.security.web.reactive.result.view.CsrfRequestDataValueProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.reeste.committee.client.BudgetClient;
import ru.reeste.committee.client.BudgetReviewsClient;
import ru.reeste.committee.controller.payload.NewBudgetReviewPayload;
import ru.reeste.committee.entity.Budget;
import ru.reeste.committee.exception.ClientBadRequestException;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("committee/budgets/{budgetId:\\d+}")
public class BudgetController {

    private final BudgetClient budgetClient;
    private final BudgetReviewsClient budgetReviewsClient;

    @ModelAttribute(name = "budget", binding = false)
    public Mono<Budget> loadBudget(@PathVariable("budgetId") int id) {
        return budgetClient.findBudget(id).switchIfEmpty(Mono.error(new NoSuchElementException("Бюджет не найден")));
    }

    @GetMapping
    public Mono<String> getBudgetPage(@ModelAttribute("budget") Mono<Budget> budgetMono, Model model) {
        return budgetMono.flatMap(
                budget -> budgetReviewsClient.findBudgetReviewByBudgetId(budget.id())
                        .collectList()
                        .doOnNext(budgetReviews -> model.addAttribute("reviews", budgetReviews))
                        .thenReturn("budgets/budget"));
    }

    @PostMapping("create-review")
    public Mono<String> createReview(@ModelAttribute("budget") Mono<Budget> budgetMono,
                                     NewBudgetReviewPayload payload,
                                     Model model) {
        return budgetMono.flatMap(
                budget -> budgetReviewsClient.createBudgetReview(budget.id(), payload.review())
                        .thenReturn("redirect:/committee/budgets/%d".formatted(budget.id()))
                        .onErrorResume(ClientBadRequestException.class,
                                exception -> {
                                    model.addAttribute("payload", payload);
                                    model.addAttribute("errors", exception.getErrors());
                                    return budgetReviewsClient.findBudgetReviewByBudgetId(budget.id())
                                            .collectList()
                                            .doOnNext(budgetReviews -> model.addAttribute("reviews", budgetReviews))
                                            .thenReturn("budgets/budget");
                                })
        );
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model) {
        model.addAttribute("error", exception.getMessage());
        return "errors/404";
    }

    @ModelAttribute
    public Mono<CsrfToken> loadCsrfToken(ServerWebExchange exchange) {
        return exchange.<Mono<CsrfToken>>getAttribute(CsrfToken.class.getName()).doOnSuccess(csrfToken -> exchange.getAttributes()
                .put(CsrfRequestDataValueProcessor.DEFAULT_CSRF_ATTR_NAME, csrfToken));
    }
}
