package com.example.recipeapi.rest_controllers;

import com.example.recipeapi.model.IngredientDetails;
import com.example.recipeapi.model.RecipeDetails;
import com.example.recipeapi.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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

    @PostMapping("/recipes/byIngredients")
    public ResponseEntity getAllRecipesByIngredients(@RequestBody Map<String, IngredientDetails> ingredientMap) {
        Map<String, IngredientDetails> map = new HashMap<>();
        for (Map.Entry<String, IngredientDetails> entry : ingredientMap.entrySet()) {
            String ingredientName = entry.getKey();
            IngredientDetails ingredientDetails = entry.getValue();
            map.put(ingredientName, ingredientDetails);
        }

        List<RecipeDetails> recipeDetails = recipeService.getRecipesFromAvailableIngredients(map);

        if (recipeDetails != null) {
            return ResponseEntity.ok(recipeDetails);
        } else {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(Collections.singletonList("Recipes not found"));
        }
    }

}
