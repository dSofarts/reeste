package ru.reeste.budget.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewBudgetLineItemPayload(
        @NotNull(message = "Название статьи бюджета должно быть заполненно")
        @Size(min = 1, message = "Название статьи бюджета должно быть длинной от {min} символов")
        String name,
        @NotNull(message = "Тип статьи должен быть выбран")
        int type) {
}
