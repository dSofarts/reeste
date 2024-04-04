package ru.reeste.committee.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.reeste.committee.client.payload.NewBudgetReviewPayload;
import ru.reeste.committee.entity.BudgetReview;
import ru.reeste.committee.exception.ClientBadRequestException;

import java.util.List;

@RequiredArgsConstructor
public class WebClientBudgetReviewsClient implements BudgetReviewsClient {

    private final WebClient webClient;

    @Override
    public Flux<BudgetReview> findBudgetReviewByBudgetId(int budgetId) {
        return webClient
                .get()
                .uri("/api/v1/review/by-budget-id/{budgetId}", budgetId)
                .retrieve()
                .bodyToFlux(BudgetReview.class);
    }

    @Override
    public Mono<BudgetReview> createBudgetReview(int budgetId, String review) {
        return webClient
                .post()
                .uri("/api/v1/review")
                .bodyValue(new NewBudgetReviewPayload(budgetId, review))
                .retrieve()
                .bodyToMono(BudgetReview.class)
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        exception -> new ClientBadRequestException(exception,
                                ((List<String>) exception.getResponseBodyAs(ProblemDetail.class)
                                        .getProperties().get("errors"))));
    }
}
