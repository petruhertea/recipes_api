package com.example.recipeapi.services;

import com.example.recipeapi.model.RecipeDetails;
import com.example.recipeapi.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    public List<RecipeDetails> getRecipeDetailsMethod() {
        return recipeRepository.getAllRecipeDetails();
    }

    public List<RecipeDetails> getRecipesFromAvailableIngredients(List<String> availableIngredients) {
        return recipeRepository.getRecipeDetailsByAvailableIngredients(availableIngredients);
    }

    public RecipeDetails getRecipeById(Integer recipeID) {
        return recipeRepository.getRecipeById(recipeID);
    }
}
