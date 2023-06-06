package com.example.Challenger2.controllers;

import com.example.Challenger2.entities.DTO.expenseDTOs.recipeDTOs.RecipeDTO;
import com.example.Challenger2.entities.Recipe;
import com.example.Challenger2.services.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/recipes")
public class RecipeController {

    @Autowired
    private RecipeService service;

    @PostMapping
    public ResponseEntity<RecipeDTO> save(@RequestBody @Valid RecipeDTO recipeDTO, UriComponentsBuilder builder) {
        Recipe insert = service.save(recipeDTO);
        URI uri = builder.path("/{id}").buildAndExpand(insert.getId()).toUri();
        return ResponseEntity.created(uri).body(new RecipeDTO(insert));
    }

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> findAll(@RequestParam(value = "search", required = false) String description) {
        List<RecipeDTO> list;

        if (description != null) {
            list = service.findByDescription(description);
            return ResponseEntity.ok().body(list);
        }

        list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RecipeDTO> findById(@PathVariable Long id) {
        RecipeDTO recipeDTO = new RecipeDTO(service.findById(id));
        return ResponseEntity.ok(recipeDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RecipeDTO> update(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO) {
        recipeDTO = new RecipeDTO(service.update(id, recipeDTO));
        return ResponseEntity.ok(recipeDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{year}/{month}")
    public ResponseEntity<List<RecipeDTO>> findByYearAndMonth(@PathVariable(value = "year") Integer year,
                                                              @PathVariable(value = "month") Integer month) {
        List<RecipeDTO> list = service.findByYearAndMonth(year, month);

        return ResponseEntity.ok().body(list);
    }

}
