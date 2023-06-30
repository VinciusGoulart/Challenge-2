package com.example.Challenger2.services;

import com.example.Challenger2.entities.DTOs.expenseDTOs.ExpenseDTO;
import com.example.Challenger2.entities.Expense;
import com.example.Challenger2.repositories.ExpenseRepository;
import com.example.Challenger2.services.exceptions.BadRequestException;
import com.example.Challenger2.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Transactional()
    public Expense save(ExpenseDTO expense) {
        if (checkDescription(expense)) {
            throw new BadRequestException("This expense already exists");
        }

        return expenseRepository.save(new Expense(expense));
    }

    @Transactional(readOnly = true)
    public Expense findById(Long id) {

        return expenseRepository.findById(id).orElseThrow(() -> new NotFoundException("Id not found"));
    }

    @Transactional(readOnly = true)
    public List<ExpenseDTO> findAll() {

        return expenseRepository.findAll().stream().map(ExpenseDTO::new).toList();
    }

    @Transactional()
    public Expense updateExpense(Long id, ExpenseDTO newExpense) {
        Expense oldExpense = findById(id);

        expenseSetter(newExpense, oldExpense);

        expenseRepository.save(oldExpense);

        return oldExpense;
    }

    @Transactional()
    public void deleteExpense(Long id) {
        Expense expense = findById(id);

        expenseRepository.delete(expense);
    }

    @Transactional(readOnly = true)
    public List<ExpenseDTO> findByDescription(String description) {
        List<ExpenseDTO> list = expenseRepository.findByDescriptionIgnoreCase(
                description).stream().map(ExpenseDTO::new).toList();

        if (list.isEmpty()) {
            throw new NotFoundException("There is no expense with this description");
        }

        return list;
    }

    @Transactional(readOnly = true)
    public List<ExpenseDTO> findByYearAndMonth(Integer year, Integer month) {
        List<ExpenseDTO> list = expenseRepository.findByYearAndMonth(
                year, month).stream().map(ExpenseDTO::new).toList();

        if (list.isEmpty()) {
            throw new NotFoundException("There is no expense on this date");
        }

        return list;
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
        oldExp.setCategory((newExp.getCategory()));
    }
}
