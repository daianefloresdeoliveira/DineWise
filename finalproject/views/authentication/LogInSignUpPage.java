package com.example.finalproject.views.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.R;

public class LogInSignUpPage extends AppCompatActivity implements View.OnClickListener {

    private String userType;
    private TextView tvDisplayText;
    private Button btnLogIn, btnSignUp, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup_page);

        // Retrieve data from the Intent
        userType = getIntent().getStringExtra("userType");
        String displayText = getIntent().getStringExtra("displayText");

        // Initialize views
        tvDisplayText = findViewById(R.id.tvDisplayText);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnBack = findViewById(R.id.btnBack);

        // Set the text based on userType
        tvDisplayText.setText(displayText);

        // Set click listeners
        btnLogIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnBack.setOnClickListener(view -> finish()); // Go back to the previous screen
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == R.id.btnLogIn) {
            intent = new Intent(this, LogInPage.class);
            intent.putExtra("userType", userType);
        } else {
            intent = new Intent(this, SignUpPage.class);
            intent.putExtra("userType", userType);
        }
        startActivity(intent);
    }
}
