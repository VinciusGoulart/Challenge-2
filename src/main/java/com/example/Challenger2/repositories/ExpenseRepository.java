package com.example.Challenger2.repositories;

import com.example.Challenger2.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT e FROM Expense e WHERE LOWER(e.description) = LOWER(:description)" +
            " AND MONTH(e.date) = :month AND YEAR(e.date) = :year")
    List<Expense> findByDescriptionAndDateMonthAndDateYearAllIgnoreCase(@Param("description") String description,
                                                                        @Param("month") int month, @Param("year") int year);
}
