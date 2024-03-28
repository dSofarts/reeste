package ru.reeste.app.clients;

import ru.reeste.app.entity.BudgetLinesItem;
import ru.reeste.app.entity.BudgetLinesType;

import java.util.List;
import java.util.Optional;

public interface BudgetLineRestClient {

    List<BudgetLinesItem> findAllBudgetLines();

    List<BudgetLinesType> findAllBudgetLinesType();

    BudgetLinesItem createBudgetLineItem(String name, int type);

    Optional<BudgetLinesItem> findBudgetLinesItem(int budgetLineId);

    void deleteBudgetLineItem(int id);
}
