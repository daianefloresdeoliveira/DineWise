package com.example.finalproject.views.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.R;
import com.example.finalproject.views.authentication.LogInSignUpPage;

public class IntroPage3 extends AppCompatActivity implements View.OnClickListener {
    Button btnClient, btnOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page3);
        initialize();
    }

    private void initialize() {
        btnClient = findViewById(R.id.btnClient);
        btnClient.setOnClickListener(this);
        btnOwner = findViewById(R.id.btnOwner);
        btnOwner.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, LogInSignUpPage.class);
        if (view.getId() == R.id.btnClient) {
            intent.putExtra("userType", "client");
            intent.putExtra("displayText", "For foodies and takeout lovers");
        } else if (view.getId() == R.id.btnOwner) {
            intent.putExtra("userType", "owner");
            intent.putExtra("displayText", "For business owners");
        }
        startActivity(intent);
    }
}
