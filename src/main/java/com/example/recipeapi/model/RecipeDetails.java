package com.example.recipeapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RecipeDetails {

    @Id
    private Integer recipeID;
    private String recipeTitle;
    private String recipeDescription;
    private String recipeInstructions;
    private int servings;
    private int prep_time_minutes;
    private int cook_time_minutes;
    private int total_time_minutes;
    private byte[] recipeImage;
    private String ingredients;

    public Integer getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(Integer recipeID) {
        this.recipeID = recipeID;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public String getRecipeInstructions() {
        return recipeInstructions;
    }

    public void setRecipeInstructions(String recipeInstructions) {
        this.recipeInstructions = recipeInstructions;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getPrep_time_minutes() {
        return prep_time_minutes;
    }

    public void setPrep_time_minutes(int prep_time_minutes) {
        this.prep_time_minutes = prep_time_minutes;
    }

    public int getCook_time_minutes() {
        return cook_time_minutes;
    }

    public void setCook_time_minutes(int cook_time_minutes) {
        this.cook_time_minutes = cook_time_minutes;
    }

    public int getTotal_time_minutes() {
        return total_time_minutes;
    }

    public void setTotal_time_minutes(int total_time_minutes) {
        this.total_time_minutes = total_time_minutes;
    }

    public byte[] getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(byte[] recipeImage) {
        this.recipeImage = recipeImage;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

}

