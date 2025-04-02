package com.example.recipeapi.rest;

import com.example.recipeapi.entity.Beverage;
import com.example.recipeapi.service.BeverageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BeverageRestController {
    BeverageService beverageService;

    BeverageRestController(BeverageService beverageService) {
        this.beverageService = beverageService;
    }

    @GetMapping("/recipes/beverages/{recipeID}")
    public ResponseEntity getBeverageSuggestions(@PathVariable("recipeID") Integer recipeId) {
        List<Beverage> beverageSuggestions = beverageService.getBeverageSuggestions(recipeId);

        if (beverageSuggestions != null || recipeId != null) {
            return new ResponseEntity<>(beverageSuggestions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Beverages not found", HttpStatus.NOT_FOUND);
        }
    }
}
