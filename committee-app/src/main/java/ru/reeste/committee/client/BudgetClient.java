package ru.reeste.committee.client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.reeste.committee.entity.Budget;

public interface BudgetClient {

    Flux<Budget> findAllBudgets();

    Mono<Budget> findBudget(int id);
}
