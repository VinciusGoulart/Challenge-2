package com.example.Challenger2.repositories;

import com.example.Challenger2.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE :name")
    User findByName(@Param("name") String name);
}
