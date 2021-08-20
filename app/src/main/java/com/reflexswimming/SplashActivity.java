package com.reflexswimming;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;
import java.util.Random;

public class SplashActivity extends AppCompatActivity {
    TextView quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Objects.requireNonNull(getSupportActionBar()).hide();

        quote = findViewById(R.id.quote);
        quote.setText(new RandomQuote().getRandomQuote());
        TextView title = findViewById(R.id.title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, SignupActivity.class));
            }
        });

    }

    static class RandomQuote {
        String[] quotes = new String[]{
          "The more you dream, the further you get",
          "The price of excellence is discipline. The cost of mediocrity is disappointment",
          "If you’re going through hell, keep going",
          "The more you believe in yourself, the faster you're going to get"
          // TODO: Add more quotes
        };

        RandomQuote() {
            // Empty constructor for creating the instance while calling the getRandomQuote() method
        }

        String getRandomQuote() {
            return quotes[ new Random().nextInt(quotes.length)];
        };
    }
}
