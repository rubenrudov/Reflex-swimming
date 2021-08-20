package com.reflexswimming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    EditText fullnameEditText;
    Spinner countriesSpinner, heatsSpinner;
    Button submitButton;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sharedPref = getSharedPreferences("userDetails0", 0);
        String value = sharedPref.getString("username", null);

        if (value != null) {
            Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
            intent.putExtra("username", value);
            intent.putExtra("country", sharedPref.getString("country", null));
            intent.putExtra("favoriteHeat", sharedPref.getString("favoriteHeat", null));

            startActivity(intent);
            finish();
        } else {
            Log.d("RUBY", "NULL");
        }

        fullnameEditText = findViewById(R.id.fullnameEditText);
        countriesSpinner = findViewById(R.id.countriesSpinner);
        heatsSpinner = findViewById(R.id.heatsSpinner);
        submitButton = findViewById(R.id.submitButton);

        String[] locales = Locale.getISOCountries();
        List<String> countries = new ArrayList<>();

        for (String countryCode : locales) {
            Locale locale = new Locale("", countryCode);
            countries.add(locale.getDisplayCountry());
        }

        Collections.sort(countries);
        countries.add(0, "Country");

        ArrayAdapter<String> countriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countries);
        countriesSpinner.setAdapter(countriesAdapter);

        ArrayAdapter<CharSequence> heatsAdapter = ArrayAdapter.createFromResource(this, R.array.heats, android.R.layout.simple_spinner_dropdown_item);
        heatsSpinner.setAdapter(heatsAdapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add creds to SharedPreferences (Later to a database)..

                String[] creds = new String[]{
                        fullnameEditText.getText().toString(),
                        countriesSpinner.getSelectedItem().toString(),
                        heatsSpinner.getSelectedItem().toString()
                };

                if (validRegistration(creds[0])) {

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("username", creds[0]);
                    editor.putString("country",creds[1]);
                    editor.putString("favoriteHeat",creds[2]);
                    editor.apply();

                    Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                    intent.putExtra("username", creds[0]);
                    intent.putExtra("country", creds[1]);
                    intent.putExtra("favoriteHeat", creds[2]);

                    startActivity(intent);
                    finish();
                }
            }
            private boolean validRegistration(String username) {

                if (username.length() < 2) {
                    Toast.makeText(SignupActivity.this, "Name should be at least 2 letters", Toast.LENGTH_SHORT).show();
                }

                else if (username.length() >= 40) {
                    Toast.makeText(SignupActivity.this, "Name should contain less than 40 letters", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });
    }
}
