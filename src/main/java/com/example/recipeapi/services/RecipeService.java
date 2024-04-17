package com.example.recipeapi.services;

import com.example.recipeapi.model.RecipeDetails;
import com.example.recipeapi.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    public List<RecipeDetails> getRecipeDetailsMethod() {
        return recipeRepository.getAllRecipeDetails();
    }

    public List<RecipeDetails> getRecipesFromAvailableIngredients(Map<String, Double> availableIngredients) {
        var temp = recipeRepository.getAllRecipeDetails();
        var set = availableIngredients.keySet();

        return temp.stream()
                .filter(e -> {
                    String[] ingredients = e.getIngredients().split(",");
                    boolean hasAtLeastOneIngredient = false;
                    for (String ingredient : ingredients) {
                        String[] details = ingredient.split(":");
                        if (set.contains(details[0].trim())) {
                            hasAtLeastOneIngredient = true;
                            double weight = extractWeight(details[1].trim());
                            if (weight > availableIngredients.get(details[0].trim())) {
                                return false; // If the weight is more than available, discard recipe
                            }
                        }
                    }
                    return hasAtLeastOneIngredient; // Return true if at least one ingredient matches
                })
                .toList();
    }

    private double extractWeight(String s) {
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                start = i;
                break;
            }
        }
        int stop = 0;
        for (int i = start + 1; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                stop = i;
                break;
            }
        }
        return Double.parseDouble(s.substring(start, stop));

    }

    public RecipeDetails getRecipeById(Integer recipeID) {
        return recipeRepository.getRecipeById(recipeID);
    }
}
