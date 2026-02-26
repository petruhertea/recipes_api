package com.example.recipeapi.service;

import com.example.recipeapi.dto.IngredientDetails;
import com.example.recipeapi.dto.RecipeDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RecipeService {

    List<RecipeDTO> getAllRecipes();

    Optional<RecipeDTO> getRecipeById(Integer id);

    List<RecipeDTO> getRecipesByAvailableIngredients(Map<String, IngredientDetails> availableIngredients);
}
