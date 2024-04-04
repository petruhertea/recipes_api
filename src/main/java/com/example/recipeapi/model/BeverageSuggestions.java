package com.example.recipeapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BeverageSuggestions {
    @Id
    private Integer beverageID;

    private String beverageSuggestions;
    private byte[] beverageImage;

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

    public byte[] getBeverageImage() {
        return beverageImage;
    }

    public void setBeverageImage(byte[] beverageImage) {
        this.beverageImage = beverageImage;
    }
}
