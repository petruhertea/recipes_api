package com.example.recipeapi.mapper;


import com.example.recipeapi.dto.RecipeDTO;
import com.example.recipeapi.entity.Recipe;
import com.example.recipeapi.entity.RecipeIngredient;

import java.util.List;

/**
 * Stateless mapper: Recipe entity → RecipeDTO.
 * Keeps conversion logic out of both the service and the entity.
 */
public class RecipeMapper {

    private RecipeMapper() {
    }   // utility class – no instantiation

    public static RecipeDTO toDTO(Recipe recipe) {
        RecipeDTO dto = new RecipeDTO();
        dto.setId(recipe.getId());
        dto.setTitle(recipe.getTitle());
        dto.setDescription(recipe.getDescription());
        dto.setInstructions(recipe.getInstructions());
        dto.setServings(recipe.getServings());
        dto.setPrepTimeMinutes(recipe.getPrepTimeMinutes());
        dto.setCookTimeMinutes(recipe.getCookTimeMinutes());
        dto.setTotalTimeMinutes(recipe.getTotalTimeMinutes());
        dto.setRecipeImage(recipe.getRecipeImage());

        List<RecipeDTO.IngredientLineDTO> lines = recipe.getRecipeIngredients()
                .stream()
                .map(RecipeMapper::toIngredientLine)
                .toList();
        dto.setIngredients(lines);

        return dto;
    }

    private static RecipeDTO.IngredientLineDTO toIngredientLine(RecipeIngredient ri) {
        return new RecipeDTO.IngredientLineDTO(
                ri.getIngredient().getName(),
                ri.getQuantity() != null ? ri.getQuantity().doubleValue() : 0,
                ri.getMeasureUnit() != null ? ri.getMeasureUnit().trim() : ""
        );
    }
}







