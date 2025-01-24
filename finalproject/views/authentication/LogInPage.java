package com.example.finalproject.views.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.R;
import com.example.finalproject.database.Database;
import com.example.finalproject.views.client.ClientHomePage;
import com.example.finalproject.views.admin.AdminHomePage;
import com.example.finalproject.views.owner.OwnerRestaurantPage;
import com.example.finalproject.views.owner.OwnerReviewsPage;
import com.example.finalproject.views.owner.OwnerTabViewAccount;
import com.example.finalproject.views.start.IntroPage1;
import com.example.finalproject.views.start.IntroPage3;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;

public class LogInPage extends AppCompatActivity {

    private FirebaseAuth auth;
    private Database database;
    private String userType;
    private EditText edEmail, edPassword;
    private Button  btnLogIn ,btnBack;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        // Initialize Firebase App Check in debug mode
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(DebugAppCheckProviderFactory.getInstance());


        auth = FirebaseAuth.getInstance();
        database = new Database();

        userType = getIntent().getStringExtra("userType");

        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);

        btnBack = findViewById(R.id.btnBackToLoginSignUp);
        btnBack.setOnClickListener(view -> finish()); // Go back to the previous screen

        tvTitle = findViewById(R.id.tvTitle);
        btnLogIn = findViewById(R.id.btnLogIn);


        tvTitle.setText(userType.equals("client") ?
                "Log In to see your favorite restaurants" :
                "Log In to see how your business is doing");

        btnLogIn.setOnClickListener(this::performLogIn);
    }

    private void performLogIn(View view) {
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser currentUser = auth.getCurrentUser();
                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    Log.d("LogInPage", "Authenticated user ID: " + userId);
                    verifyUserRole(userId);
                }
            } else {
                Toast.makeText(LogInPage.this, "Authentication failed. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void verifyUserRole(String userId) {
        database.usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String role = snapshot.child("role").getValue(String.class);
                    String expectedRole = userType.equals("client") ? "user" : "restaurant_owner";

                    if (role != null && role.equals(expectedRole)) {
                        Toast.makeText(LogInPage.this, "Login successful.", Toast.LENGTH_SHORT).show();

                        // Retrieve user details
                        String name = snapshot.child("name").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);
                        String password = snapshot.child("password").getValue(String.class);
                        String id = snapshot.child("id").getValue(String.class);

                        // Pass user details to ClientHomePage
                        if ("client".equals(userType)) {
                            Intent intent = new Intent(LogInPage.this, ClientHomePage.class);
                            intent.putExtra("name", name);
                            intent.putExtra("email", email);
                            intent.putExtra("password", password);
                            intent.putExtra("userId", id); // Pass user ID
                            startActivity(intent);
                        } else {
                            startActivity(new Intent(LogInPage.this, OwnerRestaurantPage.class));
                        }
                        finish(); // Close the login page after redirection
                    } else {
                        auth.signOut();
                        Toast.makeText(LogInPage.this, "Access denied: incorrect role.", Toast.LENGTH_SHORT).show();

                        // come back to change role
                        Intent intent = new Intent(LogInPage.this, IntroPage3.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    auth.signOut();
                    Toast.makeText(LogInPage.this, "User record not found in database.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LogInPage.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
