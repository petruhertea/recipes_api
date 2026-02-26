package com.example.recipeapi.dao;


import com.example.recipeapi.entity.Beverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BeverageRepository extends JpaRepository<Beverage, Integer> {

    /**
     * Fetch all beverages paired with a given recipe via the recipebeverage join table.
     * Uses JPQL navigation through the Recipe.beverages ManyToMany relationship.
     */
    @Query("SELECT b FROM Beverage b JOIN b.recipes r WHERE r.id = :recipeId")
    List<Beverage> findByRecipeId(Integer recipeId);
}
