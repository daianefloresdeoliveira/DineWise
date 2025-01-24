package com.example.finalproject.models;
import android.graphics.Bitmap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class Restaurant {
    private String restaurantId;
    private String name;
    private String cuisineType;
    private String address;
    private String priceRange;
    private String ownerId;
    private List<String> menuIds;
    private List<String> reviews;
    private String phoneNumber;
    private String website; // Added attribute
    private String image;
    private String createdAt; // Add this field

    public Restaurant() {
    }

    // Constructor
    public Restaurant(String restaurantId, String name, String cuisineType, String address, String priceRange,
                      String ownerId, List<String> menuIds, List<String> reviews, String phoneNumber,
                      String website, String image, String createdAt) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.cuisineType = cuisineType;
        this.address = address;
        this.priceRange = priceRange;
        this.ownerId = ownerId;
        this.menuIds = menuIds;
        this.reviews = reviews;
        this.phoneNumber = phoneNumber;
        this.website = website; // Set in constructor
        this.image = image;
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public List<String> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<String> menuIds) {
        this.menuIds = menuIds;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() { // New method
        return website;
    }

    public void setWebsite(String website) { // New method
        this.website = website;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Additional methods can be added as needed
}
