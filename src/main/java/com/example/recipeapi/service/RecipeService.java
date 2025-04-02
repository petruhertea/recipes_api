package com.example.recipeapi.service;

import com.example.recipeapi.entity.IngredientDetails;
import com.example.recipeapi.entity.RecipeDetails;

import java.util.List;
import java.util.Map;


public interface RecipeService {

    List<RecipeDetails> getAllRecipeDetails();

    List<RecipeDetails> getRecipesByAvailableIngredients(Map<String, IngredientDetails> availableIngredients);

    RecipeDetails getRecipeById(Integer recipeID);
}
