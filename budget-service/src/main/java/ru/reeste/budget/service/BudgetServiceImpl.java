package ru.reeste.budget.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.reeste.budget.entity.Budget;
import ru.reeste.budget.repository.BudgetRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    @Override
    public List<Budget> findAllBudgets() {
        return budgetRepository.findAllBudgets();
    }

    @Override
    public Budget createBudget(String name, int year, int quarter) {
        return budgetRepository.save(new Budget(null, name, year, quarter));
    }

    @Override
    public Optional<Budget> findBudget(Integer budgetId) {
        return budgetRepository.findById(budgetId);
    }

    @Override
    public void updateBudget(Integer id, String name, int year, int quarter) {
        budgetRepository.findById(id).ifPresentOrElse(budget -> {
            budget.setName(name);
            budget.setYear(year);
            budget.setQuarter(quarter);
        }, () -> {
            throw new NoSuchElementException();
        });
    }

    @Override
    public void deleteBudget(Integer id) {
        budgetRepository.deleteBudget(id);
    }
}
