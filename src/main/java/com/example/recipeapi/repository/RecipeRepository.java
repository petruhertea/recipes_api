package com.example.recipeapi.repository;

import com.example.recipeapi.model.RecipeDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends CrudRepository<RecipeDetails, Integer> {
    @Query(value = "SELECT r.recipe_id AS recipeid, r.title AS recipe_title, r.description AS recipe_description, " +
            "r.instructions AS recipe_instructions, r.servings AS servings, " +
            "r.prep_time_minutes AS prep_time_minutes, r.cook_time_minutes AS cook_time_minutes, " +
            "r.total_time_minutes AS total_time_minutes, r.recipe_image AS recipe_image, " +
            "GROUP_CONCAT(CONCAT(i.name, ': ', ri.quantity,COALESCE(CONCAT(' ', ri.measure_unit), '')) SEPARATOR ', ') AS Ingredients " +
            "FROM recipe r " +
            "LEFT JOIN recipeingredient ri ON r.recipe_id = ri.recipe_id " +
            "LEFT JOIN ingredient i ON ri.ingredient_id = i.ingredient_id " +
            "GROUP BY r.recipe_id", nativeQuery = true)
    List<RecipeDetails> getAllRecipeDetails();

    @Query(value = "SELECT r.recipe_id AS recipeid, r.title AS recipe_title, r.description AS recipe_description, " +
            "r.instructions AS recipe_instructions, r.servings AS servings, " +
            "r.prep_time_minutes AS prep_time_minutes, r.cook_time_minutes AS cook_time_minutes, " +
            "r.total_time_minutes AS total_time_minutes, r.recipe_image AS recipe_image, " +
            "GROUP_CONCAT(CONCAT(i.name, ': ', ri.quantity,COALESCE(CONCAT(' ', ri.measure_unit), '')) SEPARATOR ', ') AS Ingredients " +
            "FROM Recipe r " +
            "LEFT JOIN RecipeIngredient ri ON r.recipe_id = ri.recipe_id " +
            "LEFT JOIN Ingredient i ON ri.ingredient_id = i.ingredient_id " +
            "GROUP BY r.recipe_id " +
            "HAVING r.recipe_id = :recipe_id", nativeQuery = true)
    RecipeDetails getRecipeById(@Param("recipe_id") Integer recipeId);
}
