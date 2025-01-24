package com.example.finalproject.models;

import java.util.Date;

public class Review {
    private String reviewId;
    private String author;
    private String restaurantId;
    private String comment;
    private String date;
    private double rating;
    private int likes; // Added attribute

    public Review() {
    }

    // Constructor
    public Review(String reviewId, String author, String restaurantId, String comment, String date, double rating, int likes) {
        this.reviewId = reviewId;
        this.author = author;
        this.restaurantId = restaurantId;
        this.comment = comment;
        this.date = date;
        this.rating = rating;
        this.likes = likes; // Set in constructor
    }

    // Getters and Setters
    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getLikes() { // New method
        return likes;
    }

    public void setLikes(int likes) { // New method
        this.likes = likes;
    }

    // Additional methods can be added as needed
}
