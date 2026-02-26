package com.example.recipeapi.dto;

/**
 * Request-body DTO for POST /api/v1/recipes/byIngredients.
 * Each entry in the map represents an ingredient the user has available.
 */
public class IngredientDetails {

    private double weight;
    private String unit;

    public IngredientDetails() {
    }

    public IngredientDetails(double weight, String unit) {
        this.weight = weight;
        this.unit = unit;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return weight + " " + unit;
    }
}
