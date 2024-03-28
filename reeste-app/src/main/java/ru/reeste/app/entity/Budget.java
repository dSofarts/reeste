package ru.reeste.app.entity;

import java.util.List;

public record Budget(int id, String name, int year, int quarter, List<BudgetLine> budgetLines) {
}
