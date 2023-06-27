package com.example.Challenger2.controllers;

import com.example.Challenger2.entities.DTOs.expenseDTOs.ExpenseDTO;
import com.example.Challenger2.entities.Expense;
import com.example.Challenger2.services.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDTO> save(@RequestBody @Valid ExpenseDTO expense, UriComponentsBuilder builder) {
        Expense insert = expenseService.save(expense);

        URI uri = builder.path("/{id}").buildAndExpand(insert.getId()).toUri();

        return ResponseEntity.created(uri).body(new ExpenseDTO(insert));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ExpenseDTO> findById(@PathVariable Long id) {

        return ResponseEntity.ok(new ExpenseDTO(expenseService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> findAll(@RequestParam(value = "search", required = false) String description) {
        List<ExpenseDTO> list;

        if (description != null) {
            list = expenseService.findByDescription(description);
            return ResponseEntity.ok().body(list);
        }

        list = expenseService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ExpenseDTO> update(@PathVariable Long id, @RequestBody @Valid ExpenseDTO expenseDTO) {
        expenseDTO = new ExpenseDTO(expenseService.updateExpense(id, expenseDTO));

        return ResponseEntity.ok(expenseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        expenseService.deleteExpense(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{year}/{month}")
    public ResponseEntity<List<ExpenseDTO>> findByYearAndMonth(@PathVariable(value = "year") Integer year,
                                                               @PathVariable(value = "month") Integer month) {
        List<ExpenseDTO> list = expenseService.findByYearAndMonth(year, month);

        return ResponseEntity.ok().body(list);
    }
}
