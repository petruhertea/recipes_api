package com.example.recipeapi.rest;

import com.example.recipeapi.dto.BeverageDTO;
import com.example.recipeapi.service.BeverageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BeverageRestController {

    private final BeverageService beverageService;

    public BeverageRestController(BeverageService beverageService) {
        this.beverageService = beverageService;
    }

    @GetMapping("/recipes/{id}/beverages")
    public ResponseEntity<List<BeverageDTO>> getBeverageSuggestions(@PathVariable Integer id) {
        List<BeverageDTO> suggestions = beverageService.getBeverageSuggestions(id);
        if (suggestions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(suggestions);
    }
}
