package ru.reeste.budget.repository;

import org.springframework.data.repository.CrudRepository;
import ru.reeste.budget.entity.BudgetLinesType;

public interface BudgetLinesTypesRepository extends CrudRepository<BudgetLinesType, Integer> {
}
