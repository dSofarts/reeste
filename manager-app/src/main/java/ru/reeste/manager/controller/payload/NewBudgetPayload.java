package ru.reeste.manager.controller.payload;

public record NewBudgetPayload(
        String name,
        int year,
        int quarter) {
}
