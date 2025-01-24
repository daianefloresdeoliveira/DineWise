package com.example.finalproject.views.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.R;
import com.example.finalproject.database.Database;
import com.example.finalproject.models.Client;
import com.example.finalproject.models.Owner;
import com.example.finalproject.views.client.ClientStartupPage;
import com.example.finalproject.views.owner.OwnerStartupPage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;

public class SignUpPage extends AppCompatActivity {

    private FirebaseAuth auth;
    private Database database;
    private String userType;
    private EditText edName, edEmail, edPassword, edConfirmPassword;
    private TextView tvTitle;
    private Button btnSignUp, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);




        auth = FirebaseAuth.getInstance();
        database = new Database();

        userType = getIntent().getStringExtra("userType");

        edName = findViewById(R.id.edName);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edCreatePassword);
        edConfirmPassword = findViewById(R.id.edConfirmPassword);
        tvTitle = findViewById(R.id.tvTitle);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnBack = findViewById(R.id.btnBackToLoginSignUp2);
        btnBack.setOnClickListener(view -> finish()); // Go back to the previous screen

        tvTitle.setText(userType.equals("client") ?
                "Sign Up and start seeing your favorite restaurants today" :
                "Sign up your business today!");

        btnSignUp.setOnClickListener(this::performSignUp);
    }

    private void performSignUp(View view) {
        String name = edName.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        String confirmPassword = edConfirmPassword.getText().toString().trim();

        if (!password.equals(confirmPassword)) {
            Toast.makeText(SignUpPage.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                createUserInDatabase(name, email, password);
            } else {
                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                    Toast.makeText(SignUpPage.this, "This email is already registered.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUpPage.this, "Sign up failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createUserInDatabase(String name, String email, String password) {
        String userId = auth.getCurrentUser().getUid();
        DatabaseReference userRef = database.usersRef.child(userId);

        if ("client".equals(userType)) {
            Client newClient = new Client(userId, name, email, password);
            userRef.setValue(newClient).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpPage.this, "Client registered successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpPage.this, ClientStartupPage.class));
                } else {
                    Toast.makeText(SignUpPage.this, "Failed to save client in database.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Owner newOwner = new Owner(userId, name, email, password);
            userRef.setValue(newOwner).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpPage.this, "Owner registered successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpPage.this, OwnerStartupPage.class));
                } else {
                    Toast.makeText(SignUpPage.this, "Failed to save owner in database.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
