package com.example.recipeapi.dao;


import com.example.recipeapi.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    /**
     * Fetch all recipes with their ingredients eagerly in a single JOIN FETCH query.
     * Without JOIN FETCH, accessing recipeIngredients later would trigger N+1 queries.
     */
    @Query("SELECT DISTINCT r FROM Recipe r LEFT JOIN FETCH r.recipeIngredients ri LEFT JOIN FETCH ri.ingredient")
    List<Recipe> findAllWithIngredients();

    /**
     * Fetch a single recipe with its ingredients eagerly.
     */
    @Query("SELECT r FROM Recipe r LEFT JOIN FETCH r.recipeIngredients ri LEFT JOIN FETCH ri.ingredient WHERE r.id = :id")
    Optional<Recipe> findByIdWithIngredients(Integer id);
}
