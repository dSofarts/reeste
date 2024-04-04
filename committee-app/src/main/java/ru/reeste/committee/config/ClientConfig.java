package ru.reeste.committee.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;
import ru.reeste.committee.client.WebClientBudgetClient;
import ru.reeste.committee.client.WebClientBudgetReviewsClient;

@Configuration
public class ClientConfig {

    @Bean
    @Scope("prototype")
    public WebClient.Builder reesteServicesWebClientBuilder(
            ReactiveClientRegistrationRepository clientRegistrationRepository,
            ServerOAuth2AuthorizedClientRepository authorizedClientRepository
    ) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction filter =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                        clientRegistrationRepository,
                        authorizedClientRepository);
        filter.setDefaultClientRegistrationId("keycloak");
        return WebClient.builder()
                .filter(filter);
    }

    @Bean
    public WebClientBudgetClient webClientBudgetClient(
            @Value("${reeste.services.budget.uri:http://127.0.0.1:8081}") String baseUrl,
            WebClient.Builder reesteServicesWebClientBuilder
    ) {
        return new WebClientBudgetClient(reesteServicesWebClientBuilder
                .baseUrl(baseUrl)
                .build());
    }

    @Bean
    public WebClientBudgetReviewsClient webClientBudgetReviewsClient(
            @Value("${reeste.services.reviews.uri:http://127.0.0.1:8084}") String baseUrl,
            WebClient.Builder reesteServicesWebClientBuilder
    ) {
        return new WebClientBudgetReviewsClient(reesteServicesWebClientBuilder
                .baseUrl(baseUrl)
                .build());
    }
}
