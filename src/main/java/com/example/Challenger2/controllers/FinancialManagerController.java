package com.example.Challenger2.controllers;

import com.example.Challenger2.entities.FinancialManager;
import com.example.Challenger2.services.FinancialManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/results")
public class FinancialManagerController {

    @Autowired
    private FinancialManagerService financialManagerService;
    @GetMapping(value = "/{year}/{month}")
    public ResponseEntity<FinancialManager> monthBalance(@PathVariable(value = "year") Integer year,
                                                         @PathVariable(value = "month") Integer month){


        return ResponseEntity.ok(financialManagerService.monthBalance(year,month));
    }
}
