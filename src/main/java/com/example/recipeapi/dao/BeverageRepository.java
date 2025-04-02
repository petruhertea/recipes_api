package com.example.recipeapi.dao;

import com.example.recipeapi.entity.Beverage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BeverageRepository extends CrudRepository<Beverage, Integer> {
    @Query(value = "SELECT b.beverage_id AS beverageId, b.name AS beverage_suggestions, " +
            "b.beverage_image AS beverage_image " +
            "FROM recipe r " +
            "LEFT JOIN recipebeverage rb ON r.recipe_id = rb.recipe_id " +
            "LEFT JOIN beverage b ON rb.beverage_id = b.beverage_id " +
            "WHERE r.recipe_id= :recipe_id ",
            nativeQuery = true)
    List<Beverage> getBeverageSuggestions(@Param("recipe_id") Integer recipeId);
}
