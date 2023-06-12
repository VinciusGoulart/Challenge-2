package com.example.Challenger2.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinancialManager {

    private BigDecimal expenseBalance = BigDecimal.ZERO;
    private BigDecimal recipeBalance = BigDecimal.ZERO;
    private BigDecimal monthBalance = BigDecimal.ZERO;
    private List<BigDecimal> categoriesBalance;
}
