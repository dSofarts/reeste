package ru.reeste.approval.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.reeste.approval.entity.BudgetReview;

import java.util.UUID;

public interface BudgetReviewRepository extends ReactiveMongoRepository<BudgetReview, UUID> {

    Flux<BudgetReview> findAllByBudgetId(int budgetId);
}
