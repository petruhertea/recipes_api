package com.example.recipeapi.services;

import com.example.recipeapi.model.IngredientDetails;
import com.example.recipeapi.model.RecipeDetails;
import com.example.recipeapi.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Map;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    public List<RecipeDetails> getRecipeDetailsMethod() {
        return recipeRepository.getAllRecipeDetails();
    }


    public List<RecipeDetails> getRecipesFromAvailableIngredients(Map<String, IngredientDetails> availableIngredients) {
        var temp = recipeRepository.getAllRecipeDetails();

        return temp.stream()
                .filter(recipe -> {
                    String[] ingredients = recipe.getIngredients().split(",");
                    boolean hasMatchingIngredient = false;
                    for (String ingredient : ingredients) {
                        String[] details = ingredient.split(":");
                        String ingredientName = Normalizer.normalize(details[0].trim(), Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
                        double weight = extractWeight(details[1].trim());
                        String recipeUnit = details[1].trim().split(" ")[1];

                        for (String availableIngredientName : availableIngredients.keySet()) {
                            String normalizedAvailableName = Normalizer.normalize(availableIngredientName, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
                            if (ingredientName.contains(normalizedAvailableName) || normalizedAvailableName.contains(ingredientName)) {
                                hasMatchingIngredient = true;
                                IngredientDetails ingredientDetails = availableIngredients.get(availableIngredientName);

                                String requestUnit = ingredientDetails.getUnit(); // Get the request unit

                                if (!recipeUnit.equals(requestUnit)) {
                                    weight = convertUnits(requestUnit, recipeUnit, weight);
                                    recipeUnit = requestUnit;
                                }

                                if (weight > ingredientDetails.getWeight() || !recipeUnit.equals(requestUnit)) {
                                    return false; // If the weight is more than available or units don't match, discard recipe
                                }
                                break; // Found a matching ingredient, no need to check further
                            }
                        }
                    }
                    return hasMatchingIngredient; // Return true if at least one ingredient matches
                })
                .toList();
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

    public RecipeDetails getRecipeById(Integer recipeID) {
        return recipeRepository.getRecipeById(recipeID);
    }
}
