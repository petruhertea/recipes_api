package com.example.recipeapi.service;


import com.example.recipeapi.dao.BeverageRepository;
import com.example.recipeapi.dto.BeverageDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeverageServiceImpl implements BeverageService {

    private final BeverageRepository beverageRepository;

    public BeverageServiceImpl(BeverageRepository beverageRepository) {
        this.beverageRepository = beverageRepository;
    }

    @Override
    @Cacheable(value = "beverageSuggestions", key = "#recipeId")
    public List<BeverageDTO> getBeverageSuggestions(Integer recipeId) {
        return beverageRepository.findByRecipeId(recipeId)
                .stream()
                .map(b -> new BeverageDTO(b.getId(), b.getName(), b.getBeverageImage()))
                .toList();
    }
}
