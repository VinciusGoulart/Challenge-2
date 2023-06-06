package com.example.Challenger2.services;

import com.example.Challenger2.entities.DTO.expenseDTOs.recipeDTOs.RecipeDTO;
import com.example.Challenger2.entities.Recipe;
import com.example.Challenger2.repositories.RecipeRepository;
import com.example.Challenger2.services.exceptions.BadRequestException;
import com.example.Challenger2.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public Recipe save(RecipeDTO recipe) {
        if (checkDescription(recipe)) {
            throw new BadRequestException("This recipe already exists");
        }
        return recipeRepository.save(new Recipe(recipe));
    }

    public List<RecipeDTO> findAll() {
        return recipeRepository.findAll().stream().map(RecipeDTO::new).toList();
    }

    public Recipe findById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new NotFoundException("ID Not Found"));
    }

    public Recipe update(Long id, RecipeDTO recipeDTO) {
        Recipe recipe = recipeRepository.getReferenceById(id);
        updateData(recipeDTO, recipe);
        return recipeRepository.save(recipe);
    }

    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }

    public List<RecipeDTO> findByDescription(String description) {
        return recipeRepository.findByDescriptionIgnoreCase(description).stream().map(RecipeDTO::new).toList();
    }

    private Boolean checkDescription(RecipeDTO recipeDTO) {
        Integer monthValue = recipeDTO.getDate().getMonthValue();
        Integer yearValue = recipeDTO.getDate().getYear();
        List<Recipe> list = recipeRepository.findByDescriptionAndDateMonthAndDateYearAllIgnoreCase(recipeDTO.getDescription(), monthValue, yearValue);
        return !list.isEmpty();
    }

    private void updateData(RecipeDTO recipeDTO, Recipe recipe) {
        recipe.setDescription(recipeDTO.getDescription());
        recipe.setPrice(recipeDTO.getPrice());
        recipe.setDate(recipeDTO.getDate());
    }
}
