package com.example.finalproject.views.owner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.database.Database;
import com.example.finalproject.models.Menu;
import com.example.finalproject.views.start.IntroPage3;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class OwnerMenuPage extends AppCompatActivity implements ValueEventListener, View.OnClickListener, DialogInterface.OnClickListener {

    //private Database database;
    private LinearLayout menuContainer;
    private BottomNavigationView bottomNavigationView;
    ImageButton btnLogOut;

    private FirebaseDatabase database;
    private DatabaseReference restaurantRef, menuRef;
    private FirebaseAuth ownerAuth;
    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_owner_menu_page);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home) {
                startActivity(new Intent(OwnerMenuPage.this, OwnerRestaurantPage.class));
                return true;
            } else if (id == R.id.menu) {
                return true;
            } else if (id == R.id.reservation) {
                startActivity(new Intent(OwnerMenuPage.this, OwnerReservationsPage.class));
                return true;
            } else if (id == R.id.review) {
                startActivity(new Intent(OwnerMenuPage.this, OwnerReviewsPage.class));
                return true;
            } else if (id == R.id.account) {
                startActivity(new Intent(OwnerMenuPage.this, OwnerTabViewAccount.class));
                return true;
            }
            return false;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initialize();
        menuContainer = findViewById(R.id.menuContainer);

        ownerAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        restaurantRef = database.getReference("restaurants");
        menuRef = database.getReference("menu");

        String ownerId = ownerAuth.getCurrentUser().getUid();
        Query query = restaurantRef.orderByChild("ownerId").equalTo(ownerId);
        query.addListenerForSingleValueEvent(this);
    }

    private void initialize() {
        btnLogOut = findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(this);

        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Log Out ");
        alertDialog.setMessage("Are you sure you want to sign out? (y/n)");
        alertDialog.setPositiveButton("Yes", this);
        alertDialog.setNegativeButton("No", this);
    }

    private void fetchMenuItems(List<String> menuIds) {
        for (String menuId : menuIds) {
            menuRef.child(menuId).addListenerForSingleValueEvent(new ValueEventListener() {
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
                    System.err.println("Error fetching menu: " + error.getMessage());
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

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.exists()) {
            for (DataSnapshot restaurantSnapshot : snapshot.getChildren()) {
                String restaurantId = restaurantSnapshot.getKey(); // Get restaurant ID
                List<String> menuIds = (List<String>) restaurantSnapshot.child("menuIds").getValue();

                if (menuIds != null && !menuIds.isEmpty()) {
                    fetchMenuItems(menuIds);
                } else {
                    // Optional: Show a message for empty menus
                    Toast.makeText(this, "No menu items available", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    @Override
    public void onClick(DialogInterface dialogInterface, int button) {
        if (button == DialogInterface.BUTTON_POSITIVE) {
            Intent intent = new Intent(this, IntroPage3.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        else if (button == DialogInterface.BUTTON_NEGATIVE)
            dialogInterface.dismiss();
    }

    @Override
    public void onClick(View view) {
        if (view == btnLogOut) {
            // Show the logout confirmation dialog
            if (!alertDialog.create().isShowing()) {
                alertDialog.show();
            }
        }
    }
}