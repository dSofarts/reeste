package ru.reeste.budget.repository;

import org.springframework.data.repository.CrudRepository;
import ru.reeste.budget.entity.BudgetLinesItem;

public interface BudgetLinesItemRepository extends CrudRepository<BudgetLinesItem, Integer> {
}
