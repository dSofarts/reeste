package ru.reeste.app.controller.payload;

public record NewBudgetPayload(
        String name,
        int year,
        int quarter) {
}
