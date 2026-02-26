package com.example.recipeapi.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RecipeIngredientId implements Serializable {

    private Integer recipe;
    private Integer ingredient;

    public RecipeIngredientId() {
    }

    public RecipeIngredientId(Integer recipe, Integer ingredient) {
        this.recipe = recipe;
        this.ingredient = ingredient;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredientId that = (RecipeIngredientId) o;
        return Objects.equals(recipe, that.recipe) && Objects.equals(ingredient, that.ingredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipe, ingredient);
    }
}
