package com.reflexswimming;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    Bundle data;
    TextView welcomeTextView;

    RelativeLayout managementLink, recordsLink, learnLink;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Objects.requireNonNull(getSupportActionBar()).hide();

        data = getIntent().getExtras();
        assert data != null;

        welcomeTextView = findViewById(R.id.welcomeTextView);
        welcomeTextView.setText("Welcome " + data.getString("username"));

        managementLink = findViewById(R.id.option1);
        managementLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ManageActivity.class);
                startActivity(intent);
            }
        });

        recordsLink = findViewById(R.id.option2);
        recordsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RecordsActivity.class);
                intent.putExtra("username", data.getString("username"));
                intent.putExtra("country", data.getString("country"));
                intent.putExtra("favoriteHeat", data.getString("favoriteHeat"));
                startActivity(intent);
            }
        });
    }
}
