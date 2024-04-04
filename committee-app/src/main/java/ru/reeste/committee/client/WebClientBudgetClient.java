package ru.reeste.committee.client;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.reeste.committee.entity.Budget;

@RequiredArgsConstructor
public class WebClientBudgetClient implements BudgetClient {

    private final WebClient webClient;

    @Override
    public Flux<Budget> findAllBudgets() {
        return webClient
                .get()
                .uri("api/v1/budgets")
                .retrieve()
                .bodyToFlux(Budget.class);
    }

    @Override
    public Mono<Budget> findBudget(int id) {
        return webClient
                .get()
                .uri("/api/v1/budgets/{budgetId}", id)
                .retrieve()
                .bodyToMono(Budget.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }
}
