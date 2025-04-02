package com.example.recipeapi.rest;

import com.example.recipeapi.entity.IngredientDetails;
import com.example.recipeapi.entity.RecipeDetails;
import com.example.recipeapi.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RecipeRestController {
    RecipeService recipeService;

    RecipeRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes")
    public ResponseEntity getAllRecipes() {
        List<RecipeDetails> recipeDetails = recipeService.getAllRecipeDetails();

        if (recipeDetails != null) {
            return new ResponseEntity<>(recipeDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Recipes not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/recipes/{recipeID}")
    public ResponseEntity getRecipeByID(@PathVariable("recipeID") Integer recipeID) {
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

        List<RecipeDetails> recipeDetails = recipeService.getRecipesByAvailableIngredients(map);

        if (recipeDetails != null) {
            return ResponseEntity.ok(recipeDetails);
        } else {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(Collections.singletonList("Recipes not found"));
        }
    }

}
