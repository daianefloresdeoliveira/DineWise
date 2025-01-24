package com.example.finalproject.models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Client extends User{
    private List<String> favoriteCuisines;
    private List<String> reviewHistory;

    public Client() {
        super("", "", "", "", "");
        this.favoriteCuisines = new ArrayList<>();
        this.reviewHistory = new ArrayList<>();
    }

    // Constructor
    public Client(String userID, String name, String email, String password) {
        super(userID, name, email, password, "user");
        this.favoriteCuisines = new ArrayList<>();
        this.reviewHistory = new ArrayList<>();
    }

    public List<String> getReviewHistory() {
        return reviewHistory;
    }

    public void setReviewHistory(List<String> reviewHistory) {
        this.reviewHistory = reviewHistory;
    }

    public List<String> getFavoriteCuisines() {
        return favoriteCuisines;
    }

    public void setFavoriteCuisines(List<String> favoriteCuisines) {
        this.favoriteCuisines = favoriteCuisines;
    }

    @Override
    public boolean signUp() {
        // Sign-up logic here
        return true;
    }

    public List<Restaurant> viewRecommendedRestaurants(List<Restaurant> allRestaurants) {
        // Logic to view recommended restaurants
        return new ArrayList<>();
    }

    public void likeReview(String reviewID) {
        // Logic to like a review
    }

    public List<String> getFavoriteCuisine() {
        return favoriteCuisines;
    }

    public void addFavoriteCuisine(String cuisine) {
        favoriteCuisines.add(cuisine);
    }

}
