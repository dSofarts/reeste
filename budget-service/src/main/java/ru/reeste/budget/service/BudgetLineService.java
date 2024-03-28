package ru.reeste.budget.service;

import ru.reeste.budget.entity.BudgetLinesItem;
import ru.reeste.budget.entity.BudgetLinesType;

import java.util.Optional;

public interface BudgetLineService {

    Iterable<BudgetLinesItem> findAllBudgetLinesItem();

    Iterable<BudgetLinesType> findAllBudgetLinesTypes();

    BudgetLinesItem createBudgetLineItem(String name, int type);

    Optional<BudgetLinesItem> findBudgetLine(int budgetLineId);

    void deleteBudgetLineItem(int budgetLineId);
}
