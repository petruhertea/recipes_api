package com.example.recipeapi.service;

import com.example.recipeapi.dao.RecipeRepository;
import com.example.recipeapi.dto.IngredientDetails;
import com.example.recipeapi.dto.RecipeDTO;
import com.example.recipeapi.mapper.RecipeMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Cacheable("allRecipes")
    public List<RecipeDTO> getAllRecipes() {
        return recipeRepository.findAllWithIngredients()
                .stream()
                .map(RecipeMapper::toDTO)
                .toList();
    }

    @Override
    @Cacheable(value = "recipeById", key = "#id")
    public Optional<RecipeDTO> getRecipeById(Integer id) {
        return recipeRepository.findByIdWithIngredients(id)
                .map(RecipeMapper::toDTO);
    }

    // ─── Ingredient Matching ─────────────────────────────────────────────

    @Override
    public List<RecipeDTO> getRecipesByAvailableIngredients(Map<String, IngredientDetails> availableIngredients) {
        List<RecipeDTO> allRecipes = getAllRecipes();

        List<RecipeDTO> fullyMatched = new ArrayList<>();
        List<RecipeDTO> partiallyMatched = new ArrayList<>();

        for (RecipeDTO recipe : allRecipes) {
            boolean fullyMatch = true;
            boolean partialMatch = false;

            for (RecipeDTO.IngredientLineDTO line : recipe.getIngredients()) {
                String recipeIngredientName = normalize(line.getName());
                double requiredQty = line.getQuantity();
                String recipeUnit = line.getUnit();

                boolean found = false;

                for (Map.Entry<String, IngredientDetails> entry : availableIngredients.entrySet()) {
                    String availableName = normalize(entry.getKey());

                    if (recipeIngredientName.contains(availableName) || availableName.contains(recipeIngredientName)) {
                        found = true;
                        IngredientDetails available = entry.getValue();
                        double availableQty = convertToUnit(available.getWeight(), available.getUnit(), recipeUnit);

                        if (availableQty >= requiredQty) {
                            partialMatch = true;
                        } else {
                            fullyMatch = false;
                        }
                        break;
                    }
                }

                if (!found) {
                    fullyMatch = false;
                }
            }

            if (fullyMatch) {
                fullyMatched.add(recipe);
            } else if (partialMatch) {
                partiallyMatched.add(recipe);
            }
        }

        return fullyMatched.isEmpty() ? partiallyMatched : fullyMatched;
    }

    // ─── Helpers ─────────────────────────────────────────────────────────

    /**
     * Strips diacritics and lowercases for accent-insensitive comparison.
     */
    private String normalize(String text) {
        return Normalizer
                .normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .trim();
    }

    /**
     * Converts {@code amount} from {@code fromUnit} into {@code toUnit}.
     * Returns the original amount if units already match or conversion is unknown.
     */
    private double convertToUnit(double amount, String fromUnit, String toUnit) {
        if (fromUnit == null || toUnit == null || fromUnit.equalsIgnoreCase(toUnit)) {
            return amount;
        }
        // Normalise to base unit first (grams / millilitres), then to target
        double base = toBaseUnit(amount, fromUnit);
        return fromBaseUnit(base, toUnit);
    }

    private double toBaseUnit(double amount, String unit) {
        return switch (unit.toLowerCase()) {
            case "kg" -> amount * 1000;
            case "g" -> amount;
            case "mg" -> amount / 1000;
            case "l" -> amount * 1000;
            case "ml" -> amount;
            default -> amount;
        };
    }

    private double fromBaseUnit(double base, String targetUnit) {
        return switch (targetUnit.toLowerCase()) {
            case "kg" -> base / 1000;
            case "g" -> base;
            case "mg" -> base * 1000;
            case "l" -> base / 1000;
            case "ml" -> base;
            default -> base;
        };
    }
}
