package com.example.recipeapi.service;

import com.example.recipeapi.entity.Beverage;

import java.util.List;

public interface BeverageService {
    List<Beverage> getBeverageSuggestions(Integer recipeId);
}
