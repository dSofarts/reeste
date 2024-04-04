package ru.reeste.approval.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetReview {

    @Id
    private UUID id;
    private int budgetId;
    private String review;
    private String userId;
}
