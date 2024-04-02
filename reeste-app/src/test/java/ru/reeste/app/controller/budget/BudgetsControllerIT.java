package ru.reeste.app.controller.budget;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.reeste.app.entity.Budget;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WireMockTest(httpPort = 54321)
public class BudgetsControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void budgetList_ReturnBudgetsListPage() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/budgets")
                .with(user("manager").roles("MANAGER"));

        WireMock.stubFor(WireMock.get(WireMock.urlPathMatching("/api/v1/budgets"))
                .willReturn(WireMock.ok("""
                        [
                            {"id": 2, "name": "Бюджет 2", "year": 2023, "quarter": 2, "budgetLines":[]}
                        ]
                        """).withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        view().name("budget/budgets"),
                        model().attribute("budgets", List.of(
                                new Budget(2, "Бюджет 2", 2023, 2, new ArrayList<>())))
                );

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlPathMatching("/api/v1/budgets")));
    }

    @Test
    void getNewBudgetPage_ReturnsBudgetPage() throws Exception {

        var requestBuilder = MockMvcRequestBuilders.get("/budgets/create")
                .with(user("manager").roles("MANAGER"));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        view().name("budget/createBudget")
                );
    }
}
