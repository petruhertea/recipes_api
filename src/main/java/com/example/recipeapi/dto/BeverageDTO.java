package com.example.recipeapi.dto;

/**
 * DTO returned by the API for beverage suggestion endpoints.
 * Replaces the old Beverage pseudo-entity used as a query result holder.
 */
public class BeverageDTO {

    private Integer id;
    private String name;
    private String beverageImage;

    public BeverageDTO() {
    }

    public BeverageDTO(Integer id, String name, String beverageImage) {
        this.id = id;
        this.name = name;
        this.beverageImage = beverageImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeverageImage() {
        return beverageImage;
    }

    public void setBeverageImage(String beverageImage) {
        this.beverageImage = beverageImage;
    }
}
