package com.example.finalproject.views.client;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.database.Database;
import com.example.finalproject.models.Client;
import com.example.finalproject.models.Restaurant;
import com.example.finalproject.models.RestaurantCardViewModel;
import com.example.finalproject.views.owner.OwnerMenuPage;
import com.example.finalproject.views.owner.OwnerReservationsPage;
import com.example.finalproject.views.owner.OwnerRestaurantPage;
import com.example.finalproject.views.owner.OwnerReviewsPage;
import com.example.finalproject.views.owner.OwnerTabViewAccount;
import com.example.finalproject.views.start.IntroPage3;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClientHomePage extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnClickListener {

    private ImageButton btnLogOut;
    private LinearLayout topRestaurantsLayout, nearMeLayout;
    private Database database;
    AlertDialog.Builder alertDialog;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home_page);

        btnLogOut = findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(this);

        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Log Out");
        alertDialog.setMessage("Are you sure you want to sign out? (y/n)");
        alertDialog.setPositiveButton("Yes", this);
        alertDialog.setNegativeButton("No", this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home) {
                return true;
            } else if (id == R.id.search) {
                startActivity(new Intent(ClientHomePage.this, SearchPage.class));
                return true;
            } else if (id == R.id.review) {
                startActivity(new Intent(ClientHomePage.this, ReviewPage.class));
                return true;
            } else if (id == R.id.account) {
                startActivity(new Intent(ClientHomePage.this, ClientAccountPage.class));
                return true;
            }
            return false;
        });


        // Initialize the database object
        database = new Database();

        // Initialize layout references
        topRestaurantsLayout = findViewById(R.id.topRestaurantsContainer);
        nearMeLayout = findViewById(R.id.nearMeContainer);

        // Fetch restaurant data
        fetchRestaurants(); // Recommended
        fetchTopRatedRestaurants(); // Top Rated (demo facade)
        fetchNearbyRestaurants(); // Near Me (demo facade)
    }

    private void fetchNearbyRestaurants() {
        database.getAllRestaurants(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<RestaurantCardViewModel> nearbyRestaurants = new ArrayList<>();
                int maxDemoRestaurants = 5; // Arbitrary number of restaurants for demo
                int count = 0;

                for (DataSnapshot restaurantSnapshot : dataSnapshot.getChildren()) {
                    Restaurant restaurant = restaurantSnapshot.getValue(Restaurant.class);
                    if (restaurant != null && count < maxDemoRestaurants) {
                        RestaurantCardViewModel card = new RestaurantCardViewModel(
                                restaurantSnapshot.getKey(), // Pass ID
                                restaurant.getName(),
                                restaurant.getImage()
                        );
                        nearbyRestaurants.add(card);
                        count++;
                    }
                }

                populateRestaurantScrollView(nearMeLayout, nearbyRestaurants);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ClientHomePage", "Error fetching nearby restaurants: " + error.getMessage());
            }
        });
    }

    private void fetchTopRatedRestaurants() {
        database.getAllRestaurants(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<RestaurantCardViewModel> topRatedRestaurants = new ArrayList<>();
                int maxDemoRestaurants = 5; // Arbitrary number of restaurants for demo
                int count = 0;

                for (DataSnapshot restaurantSnapshot : dataSnapshot.getChildren()) {
                    Restaurant restaurant = restaurantSnapshot.getValue(Restaurant.class);
                    if (restaurant != null && count < maxDemoRestaurants) {
                        RestaurantCardViewModel card = new RestaurantCardViewModel(
                                restaurantSnapshot.getKey(), // Pass ID
                                restaurant.getName(),
                                restaurant.getImage()
                        );
                        topRatedRestaurants.add(card);
                        count++;
                    }
                }

                populateRestaurantScrollView(topRestaurantsLayout, topRatedRestaurants);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ClientHomePage", "Error fetching top-rated restaurants: " + error.getMessage());
            }
        });
    }

    private void fetchRestaurants() {
        String userId = getIntent().getStringExtra("userId");
        database.readClient(userId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Client client = snapshot.getValue(Client.class);
                if (client != null && client.getFavoriteCuisines() != null) {
                    List<String> favoriteCuisines = client.getFavoriteCuisines();

                    database.getAllRestaurants(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<RestaurantCardViewModel> recommendedRestaurants = new ArrayList<>();

                            for (DataSnapshot restaurantSnapshot : dataSnapshot.getChildren()) {
                                Restaurant restaurant = restaurantSnapshot.getValue(Restaurant.class);
                                if (restaurant != null && favoriteCuisines.contains(restaurant.getCuisineType())) {
                                    RestaurantCardViewModel card = new RestaurantCardViewModel(
                                            restaurantSnapshot.getKey(), // Pass ID
                                            restaurant.getName(),
                                            restaurant.getImage()
                                    );
                                    recommendedRestaurants.add(card);
                                }
                            }

                            populateRestaurantScrollView(findViewById(R.id.recommendedContainer), recommendedRestaurants);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle database error
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }

    private void populateRestaurantScrollView(LinearLayout layout, List<RestaurantCardViewModel> restaurants) {
        for (RestaurantCardViewModel restaurant : restaurants) {
            View restaurantCard = LayoutInflater.from(this).inflate(R.layout.restaurant_card, layout, false);

            ImageView imageView = restaurantCard.findViewById(R.id.restaurantImage);
            TextView textView = restaurantCard.findViewById(R.id.restaurantName);

            textView.setText(restaurant.getName());
            Glide.with(this).load(restaurant.getImageUrl()).into(imageView);

            restaurantCard.setOnClickListener(v -> {
                Intent intent = new Intent(ClientHomePage.this, RestaurantInfoActivity.class);
                intent.putExtra("restaurantName", restaurant.getName());
                intent.putExtra("restaurantImage", restaurant.getImageUrl());
                intent.putExtra("restaurantId", restaurant.getId()); // Pass ID
                startActivity(intent);
            });
            layout.addView(restaurantCard);
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int button) {
        if (button == DialogInterface.BUTTON_POSITIVE) {
            Intent intent = new Intent(this, IntroPage3.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (button == DialogInterface.BUTTON_NEGATIVE) {
            dialogInterface.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogOut) {
            if (!alertDialog.create().isShowing()) {
                alertDialog.show();
            }
        }
    }
}
