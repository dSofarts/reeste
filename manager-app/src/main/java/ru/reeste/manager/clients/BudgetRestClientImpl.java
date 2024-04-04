package ru.reeste.manager.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.reeste.manager.controller.payload.NewBudgetPayload;
import ru.reeste.manager.controller.payload.UpdateBudgetPayload;
import ru.reeste.manager.entity.Budget;
import ru.reeste.manager.exception.BadRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class BudgetRestClientImpl implements BudgetRestClient {

    private static final ParameterizedTypeReference<List<Budget>> TYPE_REFERENCE = new ParameterizedTypeReference<>(){};

    private final RestClient restClient;

    @Override
    public List<Budget> findAllBudgets() {
        return restClient
                .get()
                .uri("/api/v1/budgets")
                .retrieve()
                .body(TYPE_REFERENCE);
    }

    @Override
    public Budget createBudget(String name, int year, int quarter) {
        try {
            return restClient
                    .post()
                    .uri("/api/v1/budgets")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewBudgetPayload(name, year, quarter))
                    .retrieve()
                    .body(Budget.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Budget> findBudget(int budgetId) {
        try {
            return Optional.ofNullable(restClient
                    .get()
                    .uri("/api/v1/budgets/{budgetId}", budgetId)
                    .retrieve().body(Budget.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateBudget(int budgetId, String name, int year, int quarter) {
        try {
            restClient
                    .patch()
                    .uri("/api/v1/budgets/{budgetId}", budgetId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateBudgetPayload(name, year, quarter))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteBudget(int budgetId) {
        try {
            restClient
                    .delete()
                    .uri("/api/v1/budgets/{budgetId}", budgetId)
                    .retrieve().toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
