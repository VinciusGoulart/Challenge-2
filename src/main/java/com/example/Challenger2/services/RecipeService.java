package com.example.Challenger2.services;

import com.example.Challenger2.entities.Recipe;
import com.example.Challenger2.entities.RecipeDTOs.RecipeDTO;
import com.example.Challenger2.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository repository;

    public Recipe save(RecipeDTO recipe) {
        return repository.save(new Recipe(recipe));
    }


}
