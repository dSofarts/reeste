package ru.reeste.app.controller.budget;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import ru.reeste.manager.exception.BadRequestException;
import ru.reeste.manager.clients.BudgetRestClient;
import ru.reeste.manager.controller.budget.BudgetsController;
import ru.reeste.manager.controller.payload.NewBudgetPayload;
import ru.reeste.manager.entity.Budget;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BudgetsControllerTest {

    @Mock
    BudgetRestClient budgetRestClient;

    @InjectMocks
    BudgetsController budgetsController;

    @Test
    void createBudget_RequestIsValid_ReturnsRedirectionToBudgetPage() {
        var payload = new NewBudgetPayload("Новый бюджет", 2021, 1);
        var model = new ConcurrentModel();

        doReturn(new Budget(1, "Новый бюджет", 2021, 1, new ArrayList<>()))
                .when(budgetRestClient).createBudget("Новый бюджет", 2021, 1);

        var result = budgetsController.createBudget(payload, model);
        assertEquals("redirect:/budgets/1", result);
        verify(budgetRestClient).createBudget("Новый бюджет", 2021, 1);
        verifyNoMoreInteractions(budgetRestClient);
    }

    @Test
    void createBudget_RequestIsInvalid_ReturnsBudgetFormWithErrors() {
        var payload = new NewBudgetPayload("н", 1900, 5);
        var model = new ConcurrentModel();

        doThrow(new BadRequestException(List.of("Ошибка 1", "Ошибка 2")))
                .when(budgetRestClient).createBudget("н", 1900, 5);

        var result = budgetsController.createBudget(payload, model);

        assertEquals("budget/createBudget", result);
        assertEquals(payload, model.getAttribute("payload"));
        assertEquals(List.of("Ошибка 1", "Ошибка 2"), model.getAttribute("errors"));

        verify(budgetRestClient).createBudget("н", 1900, 5);
        verifyNoMoreInteractions(budgetRestClient);
    }
}