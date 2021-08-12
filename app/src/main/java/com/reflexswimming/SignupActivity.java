package com.reflexswimming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity {

    EditText fullnameEditText;
    Spinner countriesSpinner, heatsSpinner;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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

                Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                intent.putExtra("username", fullnameEditText.getText().toString());
                intent.putExtra("country", countriesSpinner.getSelectedItem().toString());
                intent.putExtra("heat", heatsSpinner.getSelectedItem().toString());

                startActivity(intent);
                finish();
            }
        });
    }
}
