package com.example.recipeapi.service;

import com.example.recipeapi.dao.RecipeRepository;
import com.example.recipeapi.entity.IngredientDetails;
import com.example.recipeapi.entity.RecipeDetails;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Cacheable("allRecipes")
    public List<RecipeDetails> getAllRecipeDetails() {
        return recipeRepository.getAllRecipeDetails();
    }


    @Override
    public List<RecipeDetails> getRecipesByAvailableIngredients(Map<String, IngredientDetails> availableIngredients) {
        var temp = recipeRepository.getAllRecipeDetails();

        // List to store fully matched recipes
        List<RecipeDetails> fullyMatchedRecipes = new ArrayList<>();
        // List to store partially matched recipes
        List<RecipeDetails> partiallyMatchedRecipes = new ArrayList<>();

        for (RecipeDetails recipe : temp) {
            String[] ingredients = recipe.getIngredients().split(",");
            boolean fullyMatched = true;
            boolean partiallyMatched = false;

            for (String ingredient : ingredients) {
                String[] details = ingredient.split(":");
                String ingredientName = Normalizer.normalize(details[0].trim(), Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
                double weight = extractWeight(details[1].trim());
                String recipeUnit = details[1].trim().split(" ")[1];

                boolean ingredientFound = false;

                for (String availableIngredientName : availableIngredients.keySet()) {
                    String normalizedAvailableName = Normalizer.normalize(availableIngredientName, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
                    if (ingredientName.contains(normalizedAvailableName) || normalizedAvailableName.contains(ingredientName)) {
                        ingredientFound = true;
                        IngredientDetails ingredientDetails = availableIngredients.get(availableIngredientName);

                        String requestUnit = ingredientDetails.getUnit();

                        if (!recipeUnit.equals(requestUnit)) {
                            weight = convertUnits(requestUnit, recipeUnit, weight);
                            recipeUnit = requestUnit;
                        }

                        if (weight > ingredientDetails.getWeight()) {
                            fullyMatched = false;
                        } else {
                            partiallyMatched = true;
                        }
                        break;
                    }
                }

                if (!ingredientFound) {
                    fullyMatched = false;
                }
            }

            if (fullyMatched) {
                fullyMatchedRecipes.add(recipe);
            } else if (partiallyMatched) {
                partiallyMatchedRecipes.add(recipe);
            }
        }

        // Return fully matched recipes if any, otherwise return partially matched recipes
        return fullyMatchedRecipes.isEmpty() ? partiallyMatchedRecipes : fullyMatchedRecipes;
    }

    private double convertUnits(String requestUnit, String recipeUnit, double weight) {
        if (requestUnit.equals(recipeUnit)) {
            return weight;
        }

        return switch (requestUnit) {
            case "g" -> weight * 1000;
            case "l" -> weight * 1000;
            case "kg" -> weight / 1000;
            case "mg" -> weight / 1000000;
            case "ml" -> weight / 1000;
            default -> 0;
        };
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

    @Override
    @Cacheable(value = "recipeById", key = "#recipeID")
    public RecipeDetails getRecipeById(Integer recipeID) {
        return recipeRepository.getRecipeById(recipeID);
    }
}
