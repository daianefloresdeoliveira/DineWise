package com.example.finalproject.models;

public class Menu {
    private String menuId;
    private String restaurantId;
    private String name;
    private String description;
    private double price;
    private String photo;
    private boolean isTopOrdered; // Ensure this field exists

    // No-argument constructor (required by Firebase)
    public Menu() {
    }

    // Constructor with all fields
    public Menu(String menuId, String restaurantId, String name, String description, double price, String photo, boolean isTopOrdered) {
        this.menuId = menuId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.photo = photo;
        this.isTopOrdered = isTopOrdered;
    }

    // Getters and setters
    public boolean isTopOrdered() {
        return isTopOrdered;
    }

    public void setTopOrdered(boolean topOrdered) {
        isTopOrdered = topOrdered;
    }

    // Getters and Setters
    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    // Additional methods can be added as needed
}