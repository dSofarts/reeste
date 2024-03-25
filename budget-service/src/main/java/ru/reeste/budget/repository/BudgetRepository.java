package ru.reeste.budget.repository;

import ru.reeste.budget.entity.Budget;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository {
    List<Budget> findAllBudgets();

    Budget save(Budget budget);

    Optional<Budget> findById(Integer productId);

    void deleteBudget(Integer id);
}
