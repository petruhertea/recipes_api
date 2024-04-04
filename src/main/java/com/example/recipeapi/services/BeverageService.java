package com.example.recipeapi.services;

import com.example.recipeapi.model.BeverageSuggestions;
import com.example.recipeapi.repository.BeverageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeverageService {
    @Autowired
    BeverageRepository beverageRepository;
    public List<BeverageSuggestions> getBeverageDetailsMethod(Integer recipeId){
        return beverageRepository.getAllBeverageSuggestions(recipeId);
    }
}
