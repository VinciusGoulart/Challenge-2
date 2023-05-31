package com.example.Challenger2.services;

import com.example.Challenger2.entities.Recipe;
import com.example.Challenger2.entities.recipeDTOs.RecipeDTO;
import com.example.Challenger2.repositories.RecipeRepository;
import com.example.Challenger2.services.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository repository;

    public Recipe save(RecipeDTO recipe) {
        if (checkDescription(recipe)) {
            throw new BadRequestException("This recipe already exists");
        }
        return repository.save(new Recipe(recipe));
    }

    private Boolean checkDescription(RecipeDTO recipeDTO) {
        Integer monthValue = recipeDTO.getDate().getMonthValue();
        Integer yearValue = recipeDTO.getDate().getYear();
        List<Recipe> list = repository.findByDescriptionAndDateMonthAndDateYearAllIgnoreCase(recipeDTO.getDescription(), monthValue, yearValue);
        return !list.isEmpty();
    }

}
