package com.example.recipeapi.service;

import com.example.recipeapi.dto.BeverageDTO;

import java.util.List;

public interface BeverageService {
    List<BeverageDTO> getBeverageSuggestions(Integer recipeId);
}
