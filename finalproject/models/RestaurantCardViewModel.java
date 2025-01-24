package com.example.finalproject.models;

public class RestaurantCardViewModel {
    private String id; // Add restaurant ID
    private String name;
    private String imageUrl;

    public RestaurantCardViewModel(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
