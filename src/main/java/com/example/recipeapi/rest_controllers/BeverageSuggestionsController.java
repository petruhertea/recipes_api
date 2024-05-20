package com.example.recipeapi.rest_controllers;

import com.example.recipeapi.model.BeverageSuggestions;
import com.example.recipeapi.services.BeverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BeverageSuggestionsController {

    @Autowired
    BeverageService beverageService;

    @GetMapping("/recipe/suggestions")
    public ResponseEntity getBeverageSuggestions(@RequestParam("recipe_id") Integer recipeId) {
        List<BeverageSuggestions> beverageSuggestions = beverageService.getBeverageDetailsMethod(recipeId);

        if (beverageSuggestions != null || recipeId != null) {
            return new ResponseEntity<>(beverageSuggestions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Beverages not found", HttpStatus.NOT_FOUND);
        }
    }
}
