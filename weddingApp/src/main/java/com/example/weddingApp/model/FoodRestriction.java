package com.example.weddingApp.model;

import java.util.Set;

import jakarta.persistence.*;

@Entity
public class FoodRestriction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String restrictionName;

    @ManyToMany(mappedBy = "foodRestrictions")
    private Set<Guest> guests;

}
