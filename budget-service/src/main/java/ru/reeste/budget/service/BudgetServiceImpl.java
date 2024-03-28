package ru.reeste.budget.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.reeste.budget.entity.Budget;
import ru.reeste.budget.repository.BudgetRepository;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    @Override
    public Iterable<Budget> findAllBudgets() {
        return budgetRepository.findAll();
    }

    @Override
    @Transactional
    public Budget createBudget(String name, int year, int quarter) {
        return budgetRepository.save(new Budget(null, name, year, quarter, new ArrayList<>()));
    }

    @Override
    public Optional<Budget> findBudget(Integer budgetId) {
        return budgetRepository.findById(budgetId);
    }

    @Override
    @Transactional
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
    @Transactional
    public void deleteBudget(Integer id) {
        budgetRepository.deleteById(id);
    }
}
