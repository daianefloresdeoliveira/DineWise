package com.example.finalproject.views.client;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.R;
import com.example.finalproject.database.Database;
import com.example.finalproject.views.authentication.SignUpPage;
import com.example.finalproject.views.start.IntroPage3;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ReviewPage extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnClickListener{

    ImageButton btnLogOut, btnSAddReview;
    private BottomNavigationView bottomNavigationView;
    private Database database;
    String user;

    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review_page);

        initialize();

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home) {
                startActivity(new Intent(ReviewPage.this, ClientHomePage.class));
                return true;
            } else if (id == R.id.search) {
                startActivity(new Intent(ReviewPage.this, SearchPage.class));
                return true;
            } else if (id == R.id.review) {
                return true;
            } else if (id == R.id.account) {
                startActivity(new Intent(ReviewPage.this, ClientAccountPage.class));
                return true;
            }
            return false;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    private void initialize() {
        btnSAddReview = findViewById(R.id.btnSAddReview);
        btnSAddReview.setOnClickListener(this);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(this);

        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Log Out");
        alertDialog.setMessage("Are you sure you want to sign out? (y/n)");
        alertDialog.setPositiveButton("Yes", this);
        alertDialog.setNegativeButton("No", this);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int button) {
        if (button == DialogInterface.BUTTON_POSITIVE) {
            Intent intent = new Intent(this, IntroPage3.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (button == DialogInterface.BUTTON_NEGATIVE) {
            dialogInterface.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == R.id.btnLogOut) {
            if (!alertDialog.create().isShowing()) {
                alertDialog.show();
            }
        } else if (view.getId() == R.id.btnSAddReview) {
            intent = new Intent(this, AddReviewPage.class);
            //intent.putExtra("user", user);
            startActivity(intent);
        }
    }
}