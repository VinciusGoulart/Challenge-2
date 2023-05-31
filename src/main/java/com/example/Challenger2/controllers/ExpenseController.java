package com.example.Challenger2.controllers;

import com.example.Challenger2.entities.Expense;
import com.example.Challenger2.entities.expenseDTOs.ExpenseDTO;
import com.example.Challenger2.services.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    @Transactional
    public ResponseEntity<ExpenseDTO> save(@RequestBody @Valid ExpenseDTO expense, UriComponentsBuilder builder) {
        Expense insert = expenseService.save(expense);

        URI uri = builder.path("/{id}").buildAndExpand(insert.getId()).toUri();

        return ResponseEntity.created(uri).body(new ExpenseDTO(insert));
    }

}
