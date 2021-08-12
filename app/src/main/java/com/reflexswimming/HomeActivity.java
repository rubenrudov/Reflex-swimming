package com.reflexswimming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    Bundle data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        data = getIntent().getExtras();
        assert data != null;
        // Log.d("RUBY", Objects.requireNonNull(data.getString("country")));
    }
}
