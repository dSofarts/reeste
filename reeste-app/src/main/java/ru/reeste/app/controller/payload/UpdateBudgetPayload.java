package ru.reeste.app.controller.payload;

public record UpdateBudgetPayload(
        String name,
        int year,
        int quarter) {
}
