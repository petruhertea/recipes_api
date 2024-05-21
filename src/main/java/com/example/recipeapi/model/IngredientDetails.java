package com.example.recipeapi.model;

public class IngredientDetails {
    private final double weight;
    private String unit;

    public IngredientDetails(double weight, String unit) {
        this.weight = weight;
        this.unit = unit;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {

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
