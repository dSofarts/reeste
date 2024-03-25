package ru.reeste.budget.service;

import ru.reeste.budget.entity.Budget;

import java.util.List;
import java.util.Optional;

public interface BudgetService {
    Iterable<Budget> findAllBudgets();

    Budget createBudget(String name, int year, int quarter);

    Optional<Budget> findBudget(Integer budgetId);

    void updateBudget(Integer id, String name, int year, int quarter);

    void deleteBudget(Integer id);
}
