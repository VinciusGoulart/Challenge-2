package com.example.Challenger2.entities;

import com.example.Challenger2.entities.DTO.expenseDTOs.recipeDTOs.RecipeDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_recipes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String description;
    private BigDecimal price;
    @Column(name = "date_time")
    private LocalDate date;

    public Recipe(RecipeDTO recipeDTO) {
        description = recipeDTO.getDescription();
        price = recipeDTO.getPrice();
        date = recipeDTO.getDate();
    }
}


