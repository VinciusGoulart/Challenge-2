package com.example.Challenger2.entities.DTOs.utils;

import com.example.Challenger2.entities.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinancialManagerDTO {

    private BigDecimal expenseBalance = BigDecimal.ZERO;
    private BigDecimal revenueBalance = BigDecimal.ZERO;
    private BigDecimal monthBalance = BigDecimal.ZERO;
    private HashMap<Category, BigDecimal> categoriesBalance = new HashMap<>();

}
