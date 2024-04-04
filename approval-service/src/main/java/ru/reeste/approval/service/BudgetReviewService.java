package ru.reeste.approval.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.reeste.approval.entity.BudgetReview;

public interface BudgetReviewService {

    Mono<BudgetReview> createBudgetReview(int budgetId, String review, String userId);

    Flux<BudgetReview> findBudgetReviewsByBudget(int budgetId);
}
