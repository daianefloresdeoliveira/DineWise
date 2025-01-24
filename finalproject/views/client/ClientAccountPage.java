package com.example.finalproject.views.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ClientAccountPage extends AppCompatActivity implements View.OnClickListener, ValueEventListener, OnSuccessListener, OnFailureListener, OnCompleteListener {
    TextView tvFullName, tvJoinDate, tvContributions;
    ImageButton imBtnCity, imBtnWebsite, imBtnEdit, btnWriteReview, btnUploadPhoto,
            btnMyReserves, btnFavoriteRestaurants;
    Button btnView;

    private BottomNavigationView bottomNavigationView;

    DatabaseReference sRef;
    FirebaseAuth clientAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_account_page);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home) {
                startActivity(new Intent(ClientAccountPage.this, ClientHomePage.class));
                return true;
            } else if (id == R.id.search) {
                startActivity(new Intent(ClientAccountPage.this, SearchPage.class));
                return true;
            } else if (id == R.id.review) {
                startActivity(new Intent(ClientAccountPage.this, ReviewPage.class));
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

        clientAuth = FirebaseAuth.getInstance();
        // Check if user is authenticated
        if (clientAuth.getCurrentUser() != null) {
            String clientId = clientAuth.getCurrentUser().getUid();
            sRef = FirebaseDatabase.getInstance().getReference("users").child(clientId);

            // Attach single value event listener
            sRef.addListenerForSingleValueEvent(this);
        } else {
            // Handle cases where the user is not logged in
            finish();
        }
    }

    public void initialize() {
        tvFullName = findViewById(R.id.tvFullName);
        tvJoinDate = findViewById(R.id.tvJoinDate);
        tvContributions = findViewById(R.id.tvContributions);
        imBtnCity = findViewById(R.id.imBtnCity);
        imBtnWebsite = findViewById(R.id.imBtnWebsite);
        imBtnEdit = findViewById(R.id.imBtnEdit);
        btnWriteReview = findViewById(R.id.btnWriteReview);
        btnUploadPhoto = findViewById(R.id.btnUploadPhoto);
        btnMyReserves = findViewById(R.id.btnMyReserves);
        btnFavoriteRestaurants = findViewById(R.id.btnFavoriteRestaurants);
        btnView = findViewById(R.id.btnView);

        imBtnCity.setOnClickListener(this);
        imBtnWebsite.setOnClickListener(this);
        imBtnEdit.setOnClickListener(this);
        btnWriteReview.setOnClickListener(this);
        btnUploadPhoto.setOnClickListener(this);
        btnMyReserves.setOnClickListener(this);
        btnFavoriteRestaurants.setOnClickListener(this);
        btnView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
            // Retrieve data from the database
            String name = dataSnapshot.child("name").getValue(String.class);
            // Update the UI
            if (name != null) tvFullName.setText(name);

        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    @Override
    public void onComplete(@NonNull Task task) {

    }

    @Override
    public void onFailure(@NonNull Exception e) {

    }

    @Override
    public void onSuccess(Object o) {

    }
}