package com.example.Challenger2.entities.RecipeDTOs;

import com.example.Challenger2.entities.Recipe;
import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RecipeDTO {

    private String description;
    private BigDecimal price;
    @Column(name = "date_time")
    private LocalDate date;

    public RecipeDTO(Recipe recipe) {
        this.description = recipe.getDescription();
        this.price = recipe.getPrice();
        this.date = recipe.getDate();
    }

}
