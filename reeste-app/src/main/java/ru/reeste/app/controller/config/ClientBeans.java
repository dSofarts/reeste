package ru.reeste.app.controller.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.reeste.app.controller.clients.BudgetRestClientImpl;

@Configuration
public class ClientBeans {

    @Bean
    public BudgetRestClientImpl budgetRestClient(
            @Value("${reeste.services.budget.uri:http://127.0.0.1:8081}") String baseUri) {
        return new BudgetRestClientImpl(RestClient.builder()
                .baseUrl(baseUri)
                .build());
    }
}
