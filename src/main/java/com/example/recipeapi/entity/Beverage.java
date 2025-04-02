package com.example.recipeapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Beverage {
    @Id
    private Integer beverageID;

    private String beverageSuggestions;
    private String beverageImage;

    public Integer getBeverageID() {
        return beverageID;
    }

    public void setBeverageID(Integer beverageID) {
        this.beverageID = beverageID;
    }

    public String getBeverageSuggestions() {
        return beverageSuggestions;
    }

    public void setBeverageSuggestions(String beverageSuggestions) {
        this.beverageSuggestions = beverageSuggestions;
    }

    public String getBeverageImage() {
        return beverageImage;
    }

    public void setBeverageImage(String beverageImage) {
        this.beverageImage = beverageImage;
    }
}
