package com.example.finalproject.views.client;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.R;

public class ClientStartupPage extends AppCompatActivity {

    private TextView tvName, tvEmail, tvPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_startup_page);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPassword = findViewById(R.id.tvPassword);

        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");

        tvName.setText("Name: " + name);
        tvEmail.setText("Email: " + email);
        tvPassword.setText("Password: " + password);
    }
}
