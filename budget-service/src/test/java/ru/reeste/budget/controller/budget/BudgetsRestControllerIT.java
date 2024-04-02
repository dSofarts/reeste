package ru.reeste.budget.controller.budget;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BudgetsRestControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllBudgets_ReturnsBudgetsList() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/budgets")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_budgets")));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                        [
                            {id:  1, name: 'Бюджет АО НАК "Азот" за 1 квартал 2024 года', year: 2024, quarter: 1}
                        ]
                        """)
                );
    }

    @Test
    void createBudget_RequestIsValid_ReturnsNewBudget() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/budgets")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name": "Бюджет 2", "year": 2023, "quarter": 2}""")
                .with(jwt().jwt(builder -> builder.claim("scope", "edit_budgets")));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        header().string(HttpHeaders.LOCATION, "http://localhost/api/v1/budgets/2"),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {"id": 2, "name": "Бюджет 2", "year": 2023, "quarter": 2, "budgetLines":[]}""")
                );
    }

    @Test
    void createBudget_RequestIsInvalid_ReturnsProblemDetail() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/budgets")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name": " ", "year": 2023, "quarter": 4}""")
                .with(jwt().jwt(builder -> builder.claim("scope", "edit_budgets")));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json("""
                        {
                            "errors": ["Название бюджета должно быть длинной от 3 до 100 символов"]
                        }
                        """)
                );
    }

    @Test
    void createBudget_RequestIsInvalid_ReturnsForbbiden() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/budgets")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name": " ", "year": 2023, "quarter": 4}""")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_budgets")));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isForbidden()
                );
    }

}