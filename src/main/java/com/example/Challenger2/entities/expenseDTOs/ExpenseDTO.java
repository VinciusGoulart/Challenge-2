package com.example.Challenger2.entities.expenseDTOs;

import com.example.Challenger2.entities.Expense;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExpenseDTO {

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.01", message = "Price must be greater than or equals to 0.01")
    private BigDecimal price;

    @Column(name = "date_time")
    @NotNull(message = "Date is mandatory")
    @PastOrPresent(message = "Date must be in the past or present")
    private LocalDate date;

    public ExpenseDTO(Expense expense) {
        description = expense.getDescription();
        price = expense.getPrice();
        date = expense.getDate();
    }
}
