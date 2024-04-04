package ru.reeste.manager.clients;

import ru.reeste.manager.entity.BudgetLinesItem;
import ru.reeste.manager.entity.BudgetLinesType;

import java.util.List;
import java.util.Optional;

public interface BudgetLineRestClient {

    List<BudgetLinesItem> findAllBudgetLines();

    List<BudgetLinesType> findAllBudgetLinesType();

    BudgetLinesItem createBudgetLineItem(String name, int type);

    Optional<BudgetLinesItem> findBudgetLinesItem(int budgetLineId);

    void deleteBudgetLineItem(int id);
}
