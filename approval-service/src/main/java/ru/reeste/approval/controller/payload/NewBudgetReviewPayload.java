package ru.reeste.approval.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewBudgetReviewPayload(
        @NotNull(message = "Код бюджета не даолжен быть пустой")
        Integer budgetId,
        @NotNull(message = "Обзор не должен быть пустым")
        @Size(min = 3, max = 1000, message = "Обзор должен быть от {min} до {max} символов")
        String review) {
}
