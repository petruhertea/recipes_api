package com.example.recipeapi.dto;

import java.util.List;

/**
 * DTO returned by the API for recipe endpoints.
 * Replaces the old RecipeDetails pseudo-entity.
 */
public class RecipeDTO {

    private Integer id;
    private String title;
    private String description;
    private String instructions;
    private int servings;
    private int prepTimeMinutes;
    private int cookTimeMinutes;
    private int totalTimeMinutes;
    private String recipeImage;
    private List<IngredientLineDTO> ingredients;

    public RecipeDTO() {
    }

    // ─── Getters & Setters ───────────────────────────────────────────────

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getPrepTimeMinutes() {
        return prepTimeMinutes;
    }

    public void setPrepTimeMinutes(int prepTimeMinutes) {
        this.prepTimeMinutes = prepTimeMinutes;
    }

    public int getCookTimeMinutes() {
        return cookTimeMinutes;
    }

    public void setCookTimeMinutes(int cookTimeMinutes) {
        this.cookTimeMinutes = cookTimeMinutes;
    }

    public int getTotalTimeMinutes() {
        return totalTimeMinutes;
    }

    public void setTotalTimeMinutes(int totalTimeMinutes) {
        this.totalTimeMinutes = totalTimeMinutes;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public List<IngredientLineDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientLineDTO> ingredients) {
        this.ingredients = ingredients;
    }

    // ─── Nested DTO ──────────────────────────────────────────────────────

    /**
     * A single ingredient line: name + quantity + unit.
     * Keeps ingredient data structured rather than squashed into a CSV string.
     */
    public static class IngredientLineDTO {
        private String name;
        private double quantity;
        private String unit;

        public IngredientLineDTO() {
        }

        public IngredientLineDTO(String name, double quantity, String unit) {
            this.name = name;
            this.quantity = quantity;
            this.unit = unit;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getQuantity() {
            return quantity;
        }

        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        /**
         * Convenience: "Făină: 500.0 g" – used by ingredient-matching logic
         */
        @Override
        public String toString() {
            return name + ": " + quantity + (unit != null && !unit.isBlank() ? " " + unit : "");
        }
    }
}
