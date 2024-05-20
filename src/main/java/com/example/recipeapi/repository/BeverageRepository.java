package com.example.recipeapi.repository;

import com.example.recipeapi.model.BeverageSuggestions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BeverageRepository extends CrudRepository<BeverageSuggestions, Integer> {
    @Query(value = "SELECT b.beverage_id AS beverageId, b.name AS beverage_suggestions, " +
            "b.beverage_image AS beverage_image " +
            "FROM Recipe r " +
            "LEFT JOIN RecipeBeverage rb ON r.recipe_id = rb.recipe_id " +
            "LEFT JOIN Beverage b ON rb.beverage_id = b.beverage_id " +
            "WHERE r.recipe_id= :recipe_id ",
            nativeQuery = true)
    List<BeverageSuggestions> getAllBeverageSuggestions(@Param("recipe_id") Integer recipeId);
}
