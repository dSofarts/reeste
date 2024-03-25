package ru.reeste.budget.controller.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateBudgetPayload(
        @NotNull(message = "Название бюджета должно быть заполнено")
        @Size(min = 3, max = 100, message = "Название бюджета должно быть длинной от {min} до {max} символов")
        String name,
        @NotNull(message = "Год указан некоректно")
        @Min(value = 2020, message = "Бюджет не может быть составлен ранее 2020 года")
        @Max(value = 2030, message = "Бюджет не может быть составлен позже 2030 года")
        int year,
        @NotNull(message = "Квартал указан некоректно")
        @Min(value = 1, message = "Квартал указан некоректно")
        @Max(value = 4, message = "Квартал указан некоректно")
        int quarter) {
}
