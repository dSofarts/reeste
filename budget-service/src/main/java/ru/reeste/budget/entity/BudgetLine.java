package ru.reeste.budget.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "reeste", name = "budget_lines")
public class BudgetLine {

    @Id
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private BudgetLinesItem item;
    @NotNull
    private BigDecimal sum;
}
