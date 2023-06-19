package com.example.Challenger2.repositories;

import com.example.Challenger2.entities.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {

    @Query("SELECT r FROM Revenue r WHERE LOWER(r.description) = LOWER(:description) AND MONTH(r.date) = :month AND YEAR(r.date) = :year")
    List<Revenue> findByDescriptionAndDateMonthAndDateYearAllIgnoreCase(@Param("description") String description,
                                                                        @Param("month") Integer month,
                                                                        @Param("year") Integer year);

    @Query("SELECT r FROM Revenue r WHERE LOWER(r.description) LIKE %:description%")
    List<Revenue> findByDescriptionIgnoreCase(@Param("description") String description);

    @Query("SELECT r FROM Revenue r WHERE YEAR(r.date) = :year AND MONTH(r.date) = :month")
    List<Revenue> findByYearAndMonth(@Param("year") Integer year, @Param("month") Integer month);

}