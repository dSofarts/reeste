package ru.reeste.budget.repository;

import org.springframework.stereotype.Repository;
import ru.reeste.budget.entity.Budget;

import java.util.*;

@Repository
public class BudgetRepositoryImpl implements BudgetRepository {

    private final List<Budget> budgetList = Collections.synchronizedList(new LinkedList<>());

    public List<Budget> findAllBudgets() {
        return Collections.unmodifiableList(budgetList);
    }

    @Override
    public Budget save(Budget budget) {
        budget.setId(budgetList.stream()
                .max(Comparator.comparingInt(Budget::getId))
                .map(Budget::getId)
                .orElse(0) + 1);
        budgetList.add(budget);
        return budget;
    }

    @Override
    public Optional<Budget> findById(Integer budgetId) {
        return budgetList.stream()
                .filter(budget -> Objects.equals(budgetId, budget.getId()))
                .findFirst();
    }

    @Override
    public void deleteBudget(Integer id) {
        budgetList.removeIf(budget -> Objects.equals(id, budget.getId()));
    }
}
