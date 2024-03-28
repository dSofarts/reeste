package ru.reeste.budget.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.reeste.budget.entity.Budget;
import ru.reeste.budget.entity.BudgetLinesItem;
import ru.reeste.budget.entity.BudgetLinesType;
import ru.reeste.budget.repository.BudgetLinesItemRepository;
import ru.reeste.budget.repository.BudgetLinesTypesRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetLineServiceImpl implements BudgetLineService {

    private final BudgetLinesItemRepository budgetLinesItemRepository;
    private final BudgetLinesTypesRepository budgetLinesTypesRepository;

    @Override
    public Iterable<BudgetLinesItem> findAllBudgetLinesItem() {
        return budgetLinesItemRepository.findAll();
    }

    @Override
    public Iterable<BudgetLinesType> findAllBudgetLinesTypes() {
        return budgetLinesTypesRepository.findAll();
    }

    @Override
    @Transactional
    public BudgetLinesItem createBudgetLineItem(String name, int type) {
        return budgetLinesItemRepository.save(
                new BudgetLinesItem(null, name, budgetLinesTypesRepository.findById(type).orElse(null)));
    }

    @Override
    public Optional<BudgetLinesItem> findBudgetLine(int budgetLineId) {
        return budgetLinesItemRepository.findById(budgetLineId);
    }

    @Override
    @Transactional
    public void deleteBudgetLineItem(int budgetLineId) {
        budgetLinesItemRepository.deleteById(budgetLineId);
    }
}
