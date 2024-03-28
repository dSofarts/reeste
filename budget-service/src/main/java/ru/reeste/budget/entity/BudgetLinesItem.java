package ru.reeste.budget.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "reeste", name = "budget_lines_item")
public class BudgetLinesItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 1)
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_type", nullable = false)
    @NotNull
    private BudgetLinesType idType;
}
