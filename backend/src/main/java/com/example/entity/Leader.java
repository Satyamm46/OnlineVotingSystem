package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Leader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int experience;
    private double rating;
    private int achievements;

    public Leader() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public int getAchievements() { return achievements; }
    public void setAchievements(int achievements) { this.achievements = achievements; }
}
