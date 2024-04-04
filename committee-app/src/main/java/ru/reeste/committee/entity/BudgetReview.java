package ru.reeste.committee.entity;

import java.util.UUID;

public record BudgetReview(UUID uuid, int budgetId, String review) {
}
