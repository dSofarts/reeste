package ru.reeste.manager.controller.payload;

public record UpdateBudgetPayload(
        String name,
        int year,
        int quarter) {
}
