package com.example.Challenger2.entities;

import com.example.Challenger2.entities.expenseDTOs.ExpenseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal price;
    @Column(name = "date_time")
    private LocalDate date ;

    public Expense(ExpenseDTO expenseDTO){
        description = expenseDTO.getDescription();
        price = expenseDTO.getPrice();
        date = expenseDTO.getDate();
    }
}
