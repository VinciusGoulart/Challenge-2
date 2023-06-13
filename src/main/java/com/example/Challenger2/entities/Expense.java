package com.example.Challenger2.entities;

import com.example.Challenger2.entities.DTOs.expenseDTOs.ExpenseDTO;
import com.example.Challenger2.entities.enums.Category;
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
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Category category;

    public Expense(ExpenseDTO expenseDTO) {
        description = expenseDTO.getDescription();
        price = expenseDTO.getPrice();
        date = expenseDTO.getDate();
        category = expenseDTO.getCategory();
    }
    public Expense(Long id,String description, BigDecimal price, LocalDate date) {
        this.description = description;
        this.price = price;
        this.date = date;
    }

}
