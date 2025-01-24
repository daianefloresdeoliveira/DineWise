package com.example.finalproject.database;

import com.example.finalproject.models.Client;
import com.example.finalproject.models.Owner;
import com.example.finalproject.models.Restaurant;
import com.example.finalproject.models.Review;
import com.example.finalproject.models.Menu;
import com.example.finalproject.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import androidx.annotation.NonNull;

public class Database {

    private final FirebaseDatabase firebaseDatabase;
    public final DatabaseReference usersRef;
    private final DatabaseReference restaurantsRef;
    private final DatabaseReference menuRef;
    private final DatabaseReference reviewsRef;

    public Database() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        usersRef = firebaseDatabase.getReference("users");
        restaurantsRef = firebaseDatabase.getReference("restaurants");
        menuRef = firebaseDatabase.getReference("menu");
        reviewsRef = firebaseDatabase.getReference("reviews");
    }

    public void updateClient(String clientId, Client client, DatabaseReference.CompletionListener completionListener) {
        usersRef.child(clientId).setValue(client).addOnCompleteListener(task -> {
            if (task.isSuccessful() && completionListener != null) {
                completionListener.onComplete(null, usersRef.child(clientId));
            } else {
                if (completionListener != null) {
                    completionListener.onComplete(
                            DatabaseError.fromException(task.getException() != null ? task.getException() : new Exception("Unknown error")),
                            usersRef.child(clientId)
                    );
                }
            }
        });
    }

    public void updateOwner(String ownerId, Owner owner, DatabaseReference.CompletionListener completionListener) {
        usersRef.child(ownerId).setValue(owner).addOnCompleteListener(task -> {
            if (task.isSuccessful() && completionListener != null) {
                completionListener.onComplete(null, usersRef.child(ownerId));
            } else {
                if (completionListener != null) {
                    completionListener.onComplete(
                            DatabaseError.fromException(task.getException() != null ? task.getException() : new Exception("Unknown error")),
                            usersRef.child(ownerId)
                    );
                }
            }
        });
    }

    public void updateRestaurant(String restaurantId, Restaurant restaurant, DatabaseReference.CompletionListener completionListener) {
        restaurantsRef.child(restaurantId).setValue(restaurant).addOnCompleteListener(task -> {
            if (task.isSuccessful() && completionListener != null) {
                completionListener.onComplete(null, restaurantsRef.child(restaurantId));
            } else {
                if (completionListener != null) {
                    completionListener.onComplete(
                            DatabaseError.fromException(task.getException() != null ? task.getException() : new Exception("Unknown error")),
                            restaurantsRef.child(restaurantId)
                    );
                }
            }
        });
    }

    public void updateMenuItem(String menuId, Menu menu, DatabaseReference.CompletionListener completionListener) {
        menuRef.child(menuId).setValue(menu).addOnCompleteListener(task -> {
            if (task.isSuccessful() && completionListener != null) {
                completionListener.onComplete(null, menuRef.child(menuId));
            } else {
                if (completionListener != null) {
                    completionListener.onComplete(
                            DatabaseError.fromException(task.getException() != null ? task.getException() : new Exception("Unknown error")),
                            menuRef.child(menuId)
                    );
                }
            }
        });
    }

    public void updateReview(String reviewId, Review review, DatabaseReference.CompletionListener completionListener) {
        reviewsRef.child(reviewId).setValue(review).addOnCompleteListener(task -> {
            if (task.isSuccessful() && completionListener != null) {
                completionListener.onComplete(null, reviewsRef.child(reviewId));
            } else {
                if (completionListener != null) {
                    completionListener.onComplete(
                            DatabaseError.fromException(task.getException() != null ? task.getException() : new Exception("Unknown error")),
                            reviewsRef.child(reviewId)
                    );
                }
            }
        });
    }

    public void readClient(String clientId, ValueEventListener listener) {
        usersRef.child(clientId).addListenerForSingleValueEvent(listener);
    }

    public void readOwner(String ownerId, ValueEventListener listener) {
        usersRef.child(ownerId).addListenerForSingleValueEvent(listener);
    }

    public void readRestaurant(String restaurantId, ValueEventListener listener) {
        restaurantsRef.child(restaurantId).addListenerForSingleValueEvent(listener);
    }

    public void readMenuItem(String menuId, ValueEventListener listener) {
        menuRef.child(menuId).addListenerForSingleValueEvent(listener);
    }

    public void readReview(String reviewId, ValueEventListener listener) {
        reviewsRef.child(reviewId).addListenerForSingleValueEvent(listener);
    }


    public void getAllRestaurants(ValueEventListener listener) {
        restaurantsRef.addListenerForSingleValueEvent(listener);
    }
}
