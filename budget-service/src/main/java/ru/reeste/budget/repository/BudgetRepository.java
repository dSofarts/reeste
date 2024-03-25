package ru.reeste.budget.repository;

import org.springframework.data.repository.CrudRepository;
import ru.reeste.budget.entity.Budget;

public interface BudgetRepository extends CrudRepository<Budget, Integer> {

}
