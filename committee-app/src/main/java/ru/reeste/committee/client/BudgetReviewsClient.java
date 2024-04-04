package ru.reeste.committee.client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.reeste.committee.entity.BudgetReview;

public interface BudgetReviewsClient {

    Flux<BudgetReview> findBudgetReviewByBudgetId(int budgetId);

    Mono<BudgetReview> createBudgetReview(int budgetId, String review);
}
