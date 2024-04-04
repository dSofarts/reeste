package ru.reeste.approval.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.reeste.approval.entity.BudgetReview;
import ru.reeste.approval.repository.BudgetReviewRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BudgetReviewServiceImpl implements BudgetReviewService {

    private final BudgetReviewRepository budgetReviewRepository;
    @Override
    public Mono<BudgetReview> createBudgetReview(int budgetId, String review, String userId) {
        return budgetReviewRepository.save(new BudgetReview(UUID.randomUUID(), budgetId, review, userId));
    }

    @Override
    public Flux<BudgetReview> findBudgetReviewsByBudget(int budgetId) {
        return budgetReviewRepository.findAllByBudgetId(budgetId);
    }
}
