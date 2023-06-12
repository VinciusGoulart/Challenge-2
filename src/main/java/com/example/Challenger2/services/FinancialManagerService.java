package com.example.Challenger2.services;

import com.example.Challenger2.entities.FinancialManager;
import com.example.Challenger2.entities.Recipe;
import com.example.Challenger2.repositories.ExpenseRepository;
import com.example.Challenger2.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialManagerService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    public FinancialManager monthBalance(Integer year, Integer month) {
        FinancialManager fin = new FinancialManager();

        recipeRepository.findByYearAndMonth(year, month).forEach(recipe ->
                fin.setRecipeBalance(fin.getRecipeBalance().add(recipe.getPrice())));


        return fin;
    }
    private void recipeDataFiller(FinancialManager fin){

    }
}
