package ru.reeste.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;
import ru.reeste.app.clients.BudgetLineRestClientImpl;
import ru.reeste.app.clients.BudgetRestClientImpl;
import ru.reeste.app.security.OAuthClientHttpRequestInterceptor;

@Configuration
public class ClientBeans {

    @Bean
    public BudgetRestClientImpl budgetRestClient(
            @Value("${reeste.services.budget.uri:http://127.0.0.1:8081}") String baseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${reeste.services.budget.registration-id:keycloak}") String registrationId) {
        return new BudgetRestClientImpl(RestClient.builder()
                .baseUrl(baseUri)
                .requestInterceptor(new OAuthClientHttpRequestInterceptor(
                        new DefaultOAuth2AuthorizedClientManager(
                                clientRegistrationRepository,
                                authorizedClientRepository),
                        registrationId))
                .build());
    }

    @Bean
    public BudgetLineRestClientImpl budgetLineRestClient(
            @Value("${reeste.services.budget.uri:http://127.0.0.1:8081}") String baseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${reeste.services.budget.registration-id:keycloak}") String registrationId) {
        return new BudgetLineRestClientImpl(RestClient.builder()
                .baseUrl(baseUri)
                .requestInterceptor(new OAuthClientHttpRequestInterceptor(
                        new DefaultOAuth2AuthorizedClientManager(
                                clientRegistrationRepository,
                                authorizedClientRepository),
                        registrationId))
                .build());
    }
}
