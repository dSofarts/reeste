package ru.reeste.manager.entity;

import java.math.BigDecimal;

public record BudgetLine(int id, BudgetLinesItem item, BigDecimal sum) {
}
