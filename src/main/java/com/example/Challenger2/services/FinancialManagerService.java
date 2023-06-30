package com.example.Challenger2.services;

import com.example.Challenger2.entities.DTOs.utils.FinancialManagerDTO;
import com.example.Challenger2.entities.Expense;
import com.example.Challenger2.entities.enums.Category;
import com.example.Challenger2.repositories.ExpenseRepository;
import com.example.Challenger2.repositories.RevenueRepository;
import com.example.Challenger2.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class FinancialManagerService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private RevenueRepository revenueRepository;

    @Transactional(readOnly = true)
    public FinancialManagerDTO monthSummary(Integer year, Integer month) {
        FinancialManagerDTO fin = new FinancialManagerDTO();

        // Cycles through the possible expenses for the month and fills in the category and expense fields for the month
        expenseRepository.findByYearAndMonth(year, month).forEach(expense -> {
            fin.setExpenseBalance(fin.getExpenseBalance().add(expense.getPrice()));

            categoryMapper(fin, expense);
        });

        // Cycles through the possible revenues for the month and fills in the revenue fields for the month
        revenueRepository.findByYearAndMonth(year, month).forEach(revenue ->
                fin.setRevenueBalance(fin.getRevenueBalance().add(revenue.getPrice())));

        fin.setMonthBalance(fin.getExpenseBalance().add(fin.getRevenueBalance()));

        if (fin.getMonthBalance().equals(BigDecimal.ZERO)) {
            throw new NotFoundException("There is nothing for this month");
        }

        return fin;
    }

    private void categoryMapper(FinancialManagerDTO fin, Expense expense) {

        Category category = expense.getCategory();
        BigDecimal value = fin.getCategoriesBalance().getOrDefault(category, BigDecimal.ZERO);
        value = value.add(expense.getPrice());

        fin.getCategoriesBalance().put(category, value);
    }
}
