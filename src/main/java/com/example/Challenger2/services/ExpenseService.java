package com.example.Challenger2.services;

import com.example.Challenger2.entities.Expense;
import com.example.Challenger2.entities.expenseDTOs.ExpenseDTO;
import com.example.Challenger2.repositories.ExpenseRepository;
import com.example.Challenger2.services.exceptions.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense save(ExpenseDTO expense) {
        if (checkDescription(expense)) {
            throw new BadRequestException("This expense already exists");
        }

        return expenseRepository.save(new Expense(expense));
    }

    private Boolean checkDescription(ExpenseDTO expenseDTO) {
        int month = expenseDTO.getDate().getMonthValue();
        int year = expenseDTO.getDate().getYear();

        // Check if there is an expense with the same description and month
        List<Expense> list = expenseRepository.findByDescriptionAndDateMonthAndDateYearAllIgnoreCase(
                expenseDTO.getDescription(), month, year);

        return !list.isEmpty();
    }
}
