package com.example.recipeapi.rest;

import com.example.recipeapi.dto.IngredientDetails;
import com.example.recipeapi.dto.RecipeDTO;
import com.example.recipeapi.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class RecipeRestController {

    private final RecipeService recipeService;

    public RecipeRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes")
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        List<RecipeDTO> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Integer id) {
        return recipeService.getRecipeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/recipes/by-ingredients")
    public ResponseEntity<List<RecipeDTO>> getRecipesByIngredients(
            @RequestBody Map<String, IngredientDetails> ingredientMap) {

        List<RecipeDTO> results = recipeService.getRecipesByAvailableIngredients(ingredientMap);
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(results);
    }
}
