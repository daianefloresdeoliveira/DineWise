package com.example.finalproject.views.owner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.views.start.IntroPage3;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.appcompat.app.AlertDialog;

public class OwnerTabViewAccount extends AppCompatActivity implements View.OnClickListener, ValueEventListener, DialogInterface.OnClickListener {

    ImageView imageRestaurant;
    TextView tvTitleNameRestaurant, textRangePrice,textUpdateLinkWebSite,textUpdateAddress,textUpdatePhone;
    ImageButton btnUpdateRestaurantName, btnUpdateRangePrice, btnUpdateLinkWebSite, btnUpdateAddress,btnUpdatePhone, btnLogOut;
    EditText editRestaurantName, editRangePrice, editWebsite, editAddress,editPhone;

    FirebaseDatabase database;
    DatabaseReference sRef;
    FirebaseAuth ownerAuth;
    AlertDialog.Builder alertDialog;
    //testing

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_owner_tab_view_account);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home) {
                startActivity(new Intent(OwnerTabViewAccount.this, OwnerRestaurantPage.class));
                return true;
            } else if (id == R.id.menu) {
                startActivity(new Intent(OwnerTabViewAccount.this, OwnerMenuPage.class));
                return true;
            } else if (id == R.id.reservation) {
                startActivity(new Intent(OwnerTabViewAccount.this, OwnerReservationsPage.class));
                return true;
            } else if (id == R.id.review) {
                startActivity(new Intent(OwnerTabViewAccount.this, OwnerReviewsPage.class));
                return true;
            } else if (id == R.id.account) {
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

        ownerAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        String ownerId = ownerAuth.getCurrentUser().getUid();

        sRef = database.getReference("restaurants");

        Query query = sRef.orderByChild("ownerId").equalTo(ownerId);
        query.addListenerForSingleValueEvent(this);
    }

    private void initialize() {
        imageRestaurant = findViewById(R.id.imageRestaurant);
        tvTitleNameRestaurant = findViewById(R.id.tvTitleNameRestaurant);
        textRangePrice = findViewById(R.id.textRangePrice);
        textUpdateLinkWebSite = findViewById(R.id.textUpdateLinkWebSite);
        textUpdateAddress = findViewById(R.id.textUpdateAddress);
        textUpdatePhone = findViewById(R.id.textUpdatePhone);
        editRestaurantName = findViewById(R.id.editRestaurantName);
        editRangePrice = findViewById(R.id.editRangePrice);
        editWebsite = findViewById(R.id.editWebsite);
        editAddress = findViewById(R.id.editAddress);
        editPhone = findViewById(R.id.editPhone);
        btnUpdateRestaurantName = findViewById(R.id.btnUpdateRestaurantName);
        btnUpdateRestaurantName.setOnClickListener(this);
        btnUpdateRangePrice = findViewById(R.id.btnUpdateRangePrice);
        btnUpdateRangePrice.setOnClickListener(this);
        btnUpdateLinkWebSite = findViewById(R.id.btnUpdateLinkWebSite);
        btnUpdateLinkWebSite.setOnClickListener(this);
        btnUpdateAddress = findViewById(R.id.btnUpdateAddress);
        btnUpdateAddress.setOnClickListener(this);
        btnUpdatePhone = findViewById(R.id.btnUpdatePhone);
        btnUpdatePhone.setOnClickListener(this);

        btnLogOut = findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(this);

        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Log Out ");
        alertDialog.setMessage("Are you sure you want to sign out? (y/n)");
        alertDialog.setPositiveButton("Yes", this);
        alertDialog.setNegativeButton("No", this);

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                // load restaurant data
                String name = snapshot.child("name").getValue(String.class);
                String priceRange = snapshot.child("priceRange").getValue(String.class);
                String website = snapshot.child("website").getValue(String.class);
                String address = snapshot.child("address").getValue(String.class);
                String phoneNumber = snapshot.child("phoneNumber").getValue(String.class);
                String imageUrl = snapshot.child("image").getValue(String.class);

                // show data
                if (name != null) tvTitleNameRestaurant.setText(name);
                if (priceRange != null) textRangePrice.setText(priceRange);
                if (website != null) textUpdateLinkWebSite.setText(website);
                if (address != null) textUpdateAddress.setText(address);
                if (phoneNumber != null) textUpdatePhone.setText(phoneNumber);

                if (imageUrl != null && !imageUrl.isEmpty()) {
                    // not working
                    Picasso.get()
                            .load(imageUrl)

                            .into(imageRestaurant);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {

        String ownerId = ownerAuth.getCurrentUser().getUid();
        Query query = sRef.orderByChild("ownerId").equalTo(ownerId);

        if (view == btnUpdateRestaurantName) {
            String newName = editRestaurantName.getText().toString().trim();
            if (!newName.isEmpty()) {
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                snapshot.getRef().child("name").setValue(newName);
                                tvTitleNameRestaurant.setText(newName);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
            }
        } else if (view == btnUpdateRangePrice) {
            String newRangePrice = editRangePrice.getText().toString().trim();
            if (!newRangePrice.isEmpty()) {
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                snapshot.getRef().child("priceRange").setValue(newRangePrice);
                                textRangePrice.setText(newRangePrice);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
            }
        } else if (view == btnUpdateLinkWebSite) {
            String newWebsite = editWebsite.getText().toString().trim();
            if (!newWebsite.isEmpty()) {
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                snapshot.getRef().child("website").setValue(newWebsite);
                                textUpdateLinkWebSite.setText(newWebsite);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
            }
        } else if (view == btnUpdateAddress) {
            String newAddress = editAddress.getText().toString().trim();
            if (!newAddress.isEmpty()) {
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                snapshot.getRef().child("address").setValue(newAddress);
                                textUpdateAddress.setText(newAddress);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
            }
        } else if (view == btnUpdatePhone) {
            String newPhone = editPhone.getText().toString().trim();
            if (!newPhone.isEmpty()) {
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                snapshot.getRef().child("phoneNumber").setValue(newPhone);
                                textUpdatePhone.setText(newPhone);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
            }
        } else if (view == btnLogOut) {
            // Show the logout confirmation dialog
            if (!alertDialog.create().isShowing()) {
                alertDialog.show();
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
}