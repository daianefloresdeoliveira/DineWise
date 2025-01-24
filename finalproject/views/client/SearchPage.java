package com.example.finalproject.views.client;

import static androidx.core.util.TypedValueCompat.dpToPx;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.adapters.GridSpacingItemDecoration;
import com.example.finalproject.adapters.RestaurantAdapter;
import com.example.finalproject.models.Restaurant;
import com.example.finalproject.views.start.IntroPage3;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import android.util.DisplayMetrics;

public class SearchPage extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnClickListener {

    private ImageButton btnLogOut;
    private BottomNavigationView bottomNavigationView;
    private AlertDialog.Builder alertDialog;

    private RecyclerView recyclerViewSearchResults;
    private RestaurantAdapter restaurantAdapter;
    private List<Restaurant> restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        initialize();

        // Initialize RecyclerView with GridLayoutManager
        recyclerViewSearchResults = findViewById(R.id.recyclerViewSearchResults);
        int spanCount = 2; // Two columns
        recyclerViewSearchResults.setLayoutManager(new GridLayoutManager(this, spanCount));

        restaurantList = new ArrayList<>();
        restaurantAdapter = new RestaurantAdapter(this, restaurantList);
        recyclerViewSearchResults.setAdapter(restaurantAdapter);

        // Add item decoration for spacing
        int spacingInPixels = dpToPx(16); // Adjust the spacing (16dp)
        recyclerViewSearchResults.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacingInPixels, true));
        // Add TextWatcher for the search bar
        TextInputEditText searchField = findViewById(R.id.searchfield);
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    searchRestaurants(s.toString().trim());
                } else {
                    restaurantList.clear();
                    restaurantAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Set up bottom navigation actions
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home) {
                startActivity(new Intent(SearchPage.this, ClientHomePage.class));
                return true;
            } else if (id == R.id.search) {
                return true;
            } else if (id == R.id.review) {
                startActivity(new Intent(SearchPage.this, ReviewPage.class));
                return true;
            } else if (id == R.id.account) {
                startActivity(new Intent(SearchPage.this, ClientAccountPage.class));
                return true;
            }
            return false;
        });
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private void initialize() {
        btnLogOut = findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(this);

        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Log Out");
        alertDialog.setMessage("Are you sure you want to sign out?");
        alertDialog.setPositiveButton("Yes", this);
        alertDialog.setNegativeButton("No", this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    private void searchRestaurants(String query) {
        FirebaseDatabase.getInstance().getReference("restaurants")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        restaurantList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Restaurant restaurant = snapshot.getValue(Restaurant.class);
                            if (restaurant != null && restaurant.getName().toLowerCase().contains(query.toLowerCase())) {
                                restaurantList.add(restaurant);
                            }
                        }
                        restaurantAdapter.notifyDataSetChanged();

                        // Log results for debugging
                        System.out.println("Query: " + query);
                        System.out.println("Results found: " + restaurantList.size());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.err.println("Error querying restaurants: " + databaseError.getMessage());
                    }
                });
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
