package com.example.finalproject.views.owner;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.R;

public class OwnerStartupPage extends AppCompatActivity {

    private TextView tvName, tvEmail, tvPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_startup_page);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPassword = findViewById(R.id.tvPassword);

        // Retrieve data from intent
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");

        // Set the values
        tvName.setText("Name: " + name);
        tvEmail.setText("Email: " + email);
        tvPassword.setText("Password: " + password);
    }
}