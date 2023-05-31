package com.example.Challenger2.repositories;

import com.example.Challenger2.entities.Expense;
import com.example.Challenger2.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT r FROM Recipe r WHERE LOWER(r.description) = LOWER(:description) AND MONTH(r.date) = :month AND YEAR(r.date) = :year")
    List<Recipe> findByDescriptionAndDateMonthAndDateYearAllIgnoreCase(@Param("description") String description,
                                                                       @Param("month") Integer month,
                                                                       @Param("year") Integer year);

}
