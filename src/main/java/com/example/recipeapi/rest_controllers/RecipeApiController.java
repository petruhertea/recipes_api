package com.example.recipeapi.rest_controllers;

import com.example.recipeapi.model.IngredientDetails;
import com.example.recipeapi.model.RecipeDetails;
import com.example.recipeapi.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RecipeApiController {

    @Autowired
    RecipeService recipeService;

    @GetMapping("/recipes")
    public ResponseEntity getAllRecipes() {
        List<RecipeDetails> recipeDetails = recipeService.getRecipeDetailsMethod();

        if (recipeDetails != null) {
            return new ResponseEntity<>(recipeDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Recipes not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/recipe")
    public ResponseEntity getRecipeByID(@RequestParam("recipe_id") Integer recipeID) {
        RecipeDetails recipeDetails = recipeService.getRecipeById(recipeID);

        if (recipeDetails != null) {
            return new ResponseEntity<>(recipeDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Recipes not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/recipes/byIngredients")
    public ResponseEntity getAllRecipesByIngredients(@RequestParam("ingredients")String[] availableIngredients) {

        //Change the code, so it accepts the IngredientDetails class as a hashmap parameter
        System.out.println(Arrays.toString(availableIngredients));
        Map<String, IngredientDetails> map = new HashMap<>();
        for (String ingredientString : availableIngredients) {
            String[] ingredientParts = ingredientString.split("=");
            String measureUnit = ingredientParts[1].split(" ")[1];
            if (ingredientParts.length == 2) {
                map.put(ingredientParts[0], new IngredientDetails(extractWeight(ingredientParts[1].trim()), measureUnit));
            } else {
                // Handle invalid ingredient format
                return new ResponseEntity<>("Invalid ingredient format: " + ingredientString, HttpStatus.BAD_REQUEST);
            }
        }

        List<RecipeDetails> recipeDetails = recipeService.getRecipesFromAvailableIngredients(map);

        if (recipeDetails != null) {
            return new ResponseEntity<>(recipeDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Recipes not found", HttpStatus.I_AM_A_TEAPOT);
        }
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


}
