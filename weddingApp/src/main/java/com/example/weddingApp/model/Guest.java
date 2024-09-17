package com.example.weddingApp.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("guestName")
    private String guestName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("rsvp")
    @Enumerated(EnumType.STRING)
    private RSVPStatus rsvp;

    @ManyToMany
    @JoinTable(name = "guest_food_restriction", joinColumns = @JoinColumn(name = "guest_id"), inverseJoinColumns = @JoinColumn(name = "restriction_id"))
    private Set<FoodRestriction> foodRestrictions;

    @JsonProperty("unlistedAllergies")
    private String unlistedAllergies;

    @JsonProperty("autobus")
    private boolean autobus;

    @JsonProperty("sleep")
    private boolean sleep;

    @JsonProperty("music")
    private String music;

    // Getters and Setters
    public Guest() {
        // default constructor
    }

}
