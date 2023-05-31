package com.example.Challenger2.services;

import com.example.Challenger2.entities.Expense;
import com.example.Challenger2.entities.expenseDTOs.ExpenseDTO;
import com.example.Challenger2.repositories.ExpenseRepository;
import com.example.Challenger2.services.exceptions.BadRequestException;
import com.example.Challenger2.services.exceptions.NotFoundException;
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

    public ExpenseDTO findById(Long id) {

        return new ExpenseDTO(expenseRepository.findById(id).orElseThrow(() -> new NotFoundException("Id not found")));
    }

    public List<ExpenseDTO> findALl() {

        return expenseRepository.findAll().stream().map(ExpenseDTO::new).toList();
    }

    public Expense updateExpense(Long id, ExpenseDTO newExpense) {
        Expense oldExpense = expenseRepository.findById(id).orElseThrow(() -> new NotFoundException("Id not found"));

        expenseSetter(newExpense, oldExpense);

        expenseRepository.save(oldExpense);

        return oldExpense;
    }

    private Boolean checkDescription(ExpenseDTO expenseDTO) {
        int month = expenseDTO.getDate().getMonthValue();
        int year = expenseDTO.getDate().getYear();

        // Check if there is an expense with the same description and month
        List<Expense> list = expenseRepository.findByDescriptionAndDateMonthAndDateYearAllIgnoreCase(
                expenseDTO.getDescription(), month, year);

        return !list.isEmpty();
    }

    private void expenseSetter(ExpenseDTO newExp, Expense oldExp) {
        oldExp.setDescription(newExp.getDescription());
        oldExp.setPrice(newExp.getPrice());
        oldExp.setDate(newExp.getDate());
    }
}
