package ru.reeste.app.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.reeste.app.controller.payload.NewBudgetLineItemPayload;
import ru.reeste.app.entity.BudgetLinesItem;
import ru.reeste.app.entity.BudgetLinesType;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class BudgetLineRestClientImpl implements BudgetLineRestClient {

    private final RestClient restClient;

    private static final ParameterizedTypeReference<List<BudgetLinesItem>> TYPE_REFERENCE_LINE = new ParameterizedTypeReference<>(){};
    private static final ParameterizedTypeReference<List<BudgetLinesType>> TYPE_REFERENCE_LINE_TYPES = new ParameterizedTypeReference<>(){};


    @Override
    public List<BudgetLinesItem> findAllBudgetLines() {
        return restClient
                .get()
                .uri("/api/v1/budget-lines")
                .retrieve()
                .body(TYPE_REFERENCE_LINE);
    }

    @Override
    public List<BudgetLinesType> findAllBudgetLinesType() {
        return restClient
                .get()
                .uri("/api/v1/budget-lines/types")
                .retrieve()
                .body(TYPE_REFERENCE_LINE_TYPES);
    }

    @Override
    public BudgetLinesItem createBudgetLineItem(String name, int type) {
        try {
            return restClient
                    .post()
                    .uri("/api/v1/budget-lines")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewBudgetLineItemPayload(name, type))
                    .retrieve()
                    .body(BudgetLinesItem.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<BudgetLinesItem> findBudgetLinesItem(int budgetLineId) {
        try {
            return Optional.ofNullable(restClient
                    .get()
                    .uri("/api/v1/budget-lines/{budgetLineId}", budgetLineId)
                    .retrieve().body(BudgetLinesItem.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteBudgetLineItem(int budgetLineId) {
        try {
            restClient
                    .delete()
                    .uri("/api/v1/budget-lines/{budgetLineId}", budgetLineId)
                    .retrieve().toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
