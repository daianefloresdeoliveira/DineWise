package com.example.finalproject.views.client;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.database.Database;
import com.example.finalproject.models.Menu;
import com.example.finalproject.models.Restaurant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RestaurantInfoActivity extends AppCompatActivity {

    private Database database;
    private LinearLayout menuContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        database = new Database();

        String restaurantName = getIntent().getStringExtra("restaurantName");
        String restaurantImage = getIntent().getStringExtra("restaurantImage");
        String restaurantId = getIntent().getStringExtra("restaurantId");

        ImageView restaurantImageView = findViewById(R.id.restaurantImageView);
        TextView restaurantNameView = findViewById(R.id.restaurantNameView);
        TextView restaurantDetailsView = findViewById(R.id.restaurantDetailsView);
        Button backButton = findViewById(R.id.btnBack);
        menuContainer = findViewById(R.id.menuContainer);

        restaurantNameView.setText(restaurantName);
        Glide.with(this).load(restaurantImage).into(restaurantImageView);

        fetchRestaurantDetails(restaurantId, restaurantDetailsView);

        backButton.setOnClickListener(v -> finish());
    }

    private void fetchRestaurantDetails(String restaurantId, TextView restaurantDetailsView) {
        database.readRestaurant(restaurantId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Restaurant restaurant = snapshot.getValue(Restaurant.class);
                if (restaurant != null) {
                    String details = "Cuisine: " + restaurant.getCuisineType() + "\n" +
                            "Address: " + restaurant.getAddress() + "\n" +
                            "Phone: " + restaurant.getPhoneNumber() + "\n" +
                            "Price Range: " + restaurant.getPriceRange() + "\n" +
                            "Website: " + restaurant.getWebsite();
                    restaurantDetailsView.setText(details);

                    fetchMenuItems(restaurant.getMenuIds());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }

    private void fetchMenuItems(List<String> menuIds) {
        for (String menuId : menuIds) {
            database.readMenuItem(menuId, new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Menu menu = snapshot.getValue(Menu.class);
                    if (menu != null) {
                        addMenuItemToUI(menu);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error
                }
            });
        }
    }

    private void addMenuItemToUI(Menu menu) {
        View menuItemView = getLayoutInflater().inflate(R.layout.menu_item_card, menuContainer, false);

        ImageView menuImage = menuItemView.findViewById(R.id.menuImage);
        TextView menuName = menuItemView.findViewById(R.id.menuName);
        TextView menuDescription = menuItemView.findViewById(R.id.menuDescription);
        TextView menuPrice = menuItemView.findViewById(R.id.menuPrice);
        TextView menuTopOrderTag = menuItemView.findViewById(R.id.menuTopOrderTag);

        menuName.setText(menu.getName());
        menuDescription.setText(menu.getDescription());
        menuPrice.setText("$" + menu.getPrice());
        if (menu.isTopOrdered()) {
            menuTopOrderTag.setVisibility(View.VISIBLE);
        }

        Glide.with(this).load(menu.getPhoto()).into(menuImage);

        menuContainer.addView(menuItemView);
    }
}
