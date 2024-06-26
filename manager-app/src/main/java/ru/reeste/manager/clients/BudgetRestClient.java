package ru.reeste.manager.clients;

import ru.reeste.manager.entity.Budget;

import java.util.List;
import java.util.Optional;

public interface BudgetRestClient {

    List<Budget> findAllBudgets();

    Budget createBudget(String name, int year, int quarter);

    Optional<Budget> findBudget(int budgetId);

    void updateBudget(int budgetId, String name, int year, int quarter);

    void deleteBudget(int budgetId);
}
