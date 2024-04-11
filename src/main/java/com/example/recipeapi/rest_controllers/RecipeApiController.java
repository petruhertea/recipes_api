package com.example.recipeapi.rest_controllers;

import com.example.recipeapi.model.BeverageSuggestions;
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
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity getAllRecipesByIngredients(@RequestParam("ingredients") List<String> availableIngredients) {

        /*
        List<Double> availableIngredientQuantities = Arrays.stream(availableQuantitiesStrings)
                .map(Double::parseDouble)
                .collect(Collectors.toList());
        */
        List<RecipeDetails> recipeDetails = recipeService.getRecipesFromAvailableIngredients(availableIngredients);

        if (recipeDetails != null) {
            return new ResponseEntity<>(recipeDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Recipes not found", HttpStatus.NOT_FOUND);
        }
    }


}
