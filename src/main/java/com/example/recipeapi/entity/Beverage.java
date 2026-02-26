package com.example.recipeapi.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "beverage")
public class Beverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beverage_id")
    private Integer id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "beverage_image", length = 180)
    private String beverageImage;

    @ManyToMany(mappedBy = "beverages")
    private Set<Recipe> recipes = new HashSet<>();

    public Beverage() {
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

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }
}






