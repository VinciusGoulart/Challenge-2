package com.example.Challenger2.controller;

import com.example.Challenger2.entities.Recipe;
import com.example.Challenger2.entities.RecipeDTOs.RecipeDTO;
import com.example.Challenger2.services.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/recipes")
public class RecipeController {

    @Autowired
    RecipeService service;

    @PostMapping
    public ResponseEntity<RecipeDTO> save(@RequestBody @Valid RecipeDTO recipeDTO, UriComponentsBuilder builder) {
        Recipe insert = service.save(recipeDTO);
        URI uri = builder.path("/{id}").buildAndExpand(insert.getId()).toUri();
        return ResponseEntity.created(uri).body(new RecipeDTO(insert));
    }




}
