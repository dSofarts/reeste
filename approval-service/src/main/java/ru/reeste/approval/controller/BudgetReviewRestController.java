package ru.reeste.approval.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.reeste.approval.controller.payload.NewBudgetReviewPayload;
import ru.reeste.approval.entity.BudgetReview;
import ru.reeste.approval.service.BudgetReviewService;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class BudgetReviewRestController {

    private final BudgetReviewService budgetReviewService;

    @GetMapping("by-budget-id/{budgetId:\\d+}")
    public Flux<BudgetReview> findBudgetReviewsByBudgetId(@PathVariable("budgetId") int budgetId) {
        return budgetReviewService.findBudgetReviewsByBudget(budgetId);
    }

    @PostMapping
    public Mono<ResponseEntity<BudgetReview>> createBudgetReview(
            Mono<JwtAuthenticationToken> authenticationTokenMono,
            @Valid @RequestBody Mono<NewBudgetReviewPayload> payloadMono,
            UriComponentsBuilder uriComponentsBuilder) {
        return authenticationTokenMono.flatMap(token -> payloadMono.flatMap(payload -> budgetReviewService.createBudgetReview(
                        payload.budgetId(), payload.review(), token.getToken().getSubject())))
                .map(budgetReview -> ResponseEntity
                        .created(uriComponentsBuilder.replacePath("/api/v1/review/{id}")
                                .build(budgetReview.getId()))
                        .body(budgetReview));
    }
}
