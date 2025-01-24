package com.example.finalproject.views.client;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddReviewPage extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_review_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initialize();

        //FACING A BUG WHEN I WANT TO NAVIGATE BACK TO THE HOME PAGE
//        bottomNavigationView = findViewById(R.id.bottom_navigation);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            int id = item.getItemId();
//
//            if (id == R.id.home) {
//                startActivity(new Intent(AddReviewPage.this, ClientHomePage.class));
//                return true;
//            } else if (id == R.id.search) {
//                startActivity(new Intent(AddReviewPage.this, SearchPage.class));
//                return true;
//            } else if (id == R.id.review) {
//                startActivity(new Intent(AddReviewPage.this, ReviewPage.class));
//                return true;
//            } else if (id == R.id.account) {
//                startActivity(new Intent(AddReviewPage.this, ClientAccountPage.class));
//                return true;
//            }
//            return false;
//        });
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }

    private void initialize() {
        //user = getIntent().getStringExtra("user");
    }
}