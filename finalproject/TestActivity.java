package com.example.finalproject;

import com.example.finalproject.database.Database;
import com.example.finalproject.models.Client;
import com.example.finalproject.models.Owner;
import com.example.finalproject.models.Restaurant;
import com.example.finalproject.models.Review;
import com.example.finalproject.models.Menu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    private Database database;
    private TextView testOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // Initialize UI components
        testOutput = findViewById(R.id.testOutput);
        database = new Database();

        // Test reading a restaurant
        database.readRestaurant("restaurant001", new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);
                if (restaurant != null) {
                    testOutput.append("Read Success: Restaurant Name: " + restaurant.getName() + "\n");
                } else {
                    testOutput.append("Read Failed: Restaurant not found.\n");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                testOutput.append("Read Error: " + databaseError.getMessage() + "\n");
            }
        });

        // Test reading a client from the users table
        database.readClient("client001", new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Client client = dataSnapshot.getValue(Client.class);
                if (client != null) {
                    testOutput.append("Read Success: Client Email: " + client.getEmail() + "\n");
                } else {
                    testOutput.append("Read Failed: Client not found.\n");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                testOutput.append("Read Error: " + databaseError.getMessage() + "\n");
            }
        });

        // Test reading an owner from the users table
        database.readOwner("owner001", new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Owner owner = dataSnapshot.getValue(Owner.class);
                if (owner != null) {
                    testOutput.append("Read Success: Owner Email: " + owner.getEmail() + "\n");
                } else {
                    testOutput.append("Read Failed: Owner not found.\n");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                testOutput.append("Read Error: " + databaseError.getMessage() + "\n");
            }
        });

        // Test reading a menu item
        database.readMenuItem("menu001", new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Menu menu = dataSnapshot.getValue(Menu.class);
                if (menu != null) {
                    testOutput.append("Read Success: Menu Item Name: " + menu.getName() + "\n");
                } else {
                    testOutput.append("Read Failed: Menu item not found.\n");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                testOutput.append("Read Error: " + databaseError.getMessage() + "\n");
            }
        });

        // Test reading a review
        database.readReview("review001", new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Review review = dataSnapshot.getValue(Review.class);
                if (review != null) {
                    testOutput.append("Read Success: Review Comment: " + review.getComment() + "\n");
                } else {
                    testOutput.append("Read Failed: Review not found.\n");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                testOutput.append("Read Error: " + databaseError.getMessage() + "\n");
            }
        });

        // Test updating a client
        Client updatedClient = new Client(
                "client001",
                "name",
                "client1_updated@example.com",
                "updatedpassword123"
        );
        updatedClient.addFavoriteCuisine("Japanese");
        database.updateClient("client001", updatedClient, (databaseError, databaseReference) -> {
            if (databaseError == null && databaseReference != null) {
                testOutput.append("Update Success: Client updated successfully.\n");
            } else {
                if (databaseError != null) {
                    testOutput.append("Update Failed: " + databaseError.getMessage() + "\n");
                } else if (databaseReference == null) {
                    testOutput.append("Update Failed: Database reference is null.\n");
                }
            }
        });

        // Test updating an owner
        Owner updatedOwner = new Owner(
                "owner001",
                "name",
                "owner1_updated@example.com",
                "updatedownerpass123"
        );
        database.updateOwner("owner001", updatedOwner, (databaseError, databaseReference) -> {
            if (databaseError == null && databaseReference != null) {
                testOutput.append("Update Success: Owner updated successfully.\n");
            } else {
                if (databaseError != null) {
                    testOutput.append("Update Failed: " + databaseError.getMessage() + "\n");
                } else if (databaseReference == null) {
                    testOutput.append("Update Failed: Database reference is null.\n");
                }
            }
        });

        // Test updating a restaurant
        Restaurant updatedRestaurant = new Restaurant(
                "restaurant001",                // restaurantId
                "Updated Restaurant Name",      // name
                "Fusion Grill",                 // cuisineType
                "101 Flame Rd",                 // address
                "$$$$",                         // priceRange
                "owner001",                     // ownerId
                List.of("menu001", "menu002"),  // menuIds
                List.of("review001", "review002"), // reviews
                "123-456-7890",                 // phoneNumber
                "http://updatedrestaurant.com", // website
                "updated_image_url",            // image
                "2024-01-01T12:00:00Z"          // createdAt
        );
        database.updateRestaurant("restaurant001", updatedRestaurant, (databaseError, databaseReference) -> {
            if (databaseError == null && databaseReference != null) {
                testOutput.append("Update Success: Restaurant updated successfully.\n");
            } else {
                if (databaseError != null) {
                    testOutput.append("Update Failed: " + databaseError.getMessage() + "\n");
                } else if (databaseReference == null) {
                    testOutput.append("Update Failed: Database reference is null.\n");
                }
            }
        });
    }
}
