package com.reflexswimming;

import androidx.appcompat.app.AppCompatActivity;
import com.blongho.country_data.World;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Objects;
import android.view.Window;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

public class RecordsActivity extends AppCompatActivity {
    Bundle data;
    TextView swimmerName, heatTitle, recordTextView;
    ImageView countryFlag, styleVector;
    Spinner heatsSpinner;
    FloatingActionButton addRecord;

    SharedPreferences sharedPrefs;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        World.init(getApplicationContext());
        Objects.requireNonNull(getSupportActionBar()).hide();

        data = getIntent().getExtras();
        sharedPrefs = getSharedPreferences("userDetails0", 0);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Personal records");
        swimmerName = findViewById(R.id.fullnameTextView);
        swimmerName.setText(data.getString("username"));
        countryFlag = findViewById(R.id.countryFlag);
        countryFlag.setImageResource(World.getFlagOf(Objects.requireNonNull(data.getString("country"))));

        styleVector = findViewById(R.id.styleVector);
        styleVector.setImageResource(getResourceByStyle(Objects.requireNonNull(data.getString("favoriteHeat"))));
        heatTitle = findViewById(R.id.title);
        recordTextView = findViewById(R.id.recordTextView);

        heatTitle.setText(data.getString("favoriteHeat"));
        recordTextView.setText(sharedPrefs.getString(data.getString("favoriteHeat"), "No record for this heat"));

        heatsSpinner = findViewById(R.id.heatsSpinner);
        String[] heats = getResources().getStringArray(R.array.heats);
        heats[0] = "Choose a heat to view";
        final ArrayAdapter<String> heatsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, heats);
        heatsSpinner.setAdapter(heatsAdapter);
        addRecord = findViewById(R.id.fab);

        if (!recordTextView.getText().toString().equals("No record for this heat")) {
            addRecord.setImageResource(R.drawable.ic_edit);
        }
        else {
            addRecord.setImageResource(R.drawable.ic_add);
        }

        heatsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Objects.requireNonNull(getSupportActionBar()).setTitle(heatsAdapter.getItem(position)); // For testing
                if (!Objects.equals(heatsAdapter.getItem(position), "Choose a heat to view")) {
                    heatTitle.setText(heatsAdapter.getItem(position));

                    String record = sharedPrefs.getString(heatsAdapter.getItem(position), "No record for this heat");
                    recordTextView.setText(record);
                    recordTextView.setVisibility(View.VISIBLE);
                    styleVector.setImageResource(getResourceByStyle(Objects.requireNonNull(heatsAdapter.getItem(position))));

                }
                else {
                    heatTitle = findViewById(R.id.title);
                    styleVector.setImageResource(getResourceByStyle(Objects.requireNonNull(data.getString("favoriteHeat"))));
                    heatTitle.setText(data.getString("favoriteHeat"));
                    recordTextView.setText(sharedPrefs.getString(data.getString("favoriteHeat"), "No record for this heat"));
                }

                if (!recordTextView.getText().toString().equals("No record for this heat")) {
                    addRecord.setImageResource(R.drawable.ic_edit);
                }
                else {
                    addRecord.setImageResource(R.drawable.ic_add);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Open custom dialog with record setting;
                String heat = (String) heatsSpinner.getSelectedItem();

                if (!heat.equals("Choose a heat to view")) {
                    final String[] times = new String[]{"00", "00", "00"};

                    Intent dialogIntent = new Intent(RecordsActivity.this, String.class);
                    dialogIntent.putExtra("heat", heat);

                    // final HeatDialog dialog = new HeatDialog(RecordsActivity.this, dialogIntent);

                    final Rect displayRectangle = new Rect();
                    Window window = RecordsActivity.this.getWindow();
                    window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(RecordsActivity.this);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.add_record_dialog, viewGroup, false);
                    dialogView.setMinimumWidth((int)(displayRectangle.width() * 1f));
                    dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
                    builder.setView(dialogView);

                    final AlertDialog alertDialog = builder.create();

                    final TextView displayPreview = dialogView.findViewById(R.id.displayPreview);
                    final Button saveButton = dialogView.findViewById(R.id.saveButton);
                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("CommitPrefEdits")
                        @Override
                        public void onClick(View v) {
                            if (times[0].equals("Minutes")) {
                                times[0] = "00";
                            }

                            if (times[1].equals("Seconds")) {
                                times[1] = "00";
                            }

                            if (times[2].equals("Millis")) {
                                times[2] = "00";
                            }

                            displayPreview.setText(times[0] + ":" + times[1] + ":" + times[2]);
                            sharedPrefs.edit().putString((String) heatsSpinner.getSelectedItem(), displayPreview.getText().toString()).apply();
                            recordTextView.setText(sharedPrefs.getString((String) heatsSpinner.getSelectedItem(), "No record for this heat"));

                            alertDialog.dismiss();
                        }
                    });

                    TextView heatTextView = dialogView.findViewById(R.id.heatTextView);
                    heatTextView = dialogView.findViewById(R.id.heatTextView);
                    Spinner minutes = dialogView.findViewById(R.id.minutesSpinner);
                    Spinner seconds = dialogView.findViewById(R.id.secondsSpinner);
                    Spinner millis = dialogView.findViewById(R.id.millisSpinner);

                    displayPreview.setText("00:00:00");
                    heatTextView.setText((String) RecordsActivity.this.heatsSpinner.getSelectedItem());

                    String[] to60min = new String[61];
                    to60min[0] = "Minutes";
                    for (int i = 1; i <  to60min.length; i++) {
                        if (i <= 10) {
                            to60min[i] = "0" + (i - 1);
                        }
                        else {
                            to60min[i] = String.valueOf(i - 1);
                        }
                    }

                    String[] to60sec = new String[61];
                    to60sec[0] = "Seconds";
                    for (int i = 1; i <  to60sec.length; i++) {
                        if (i <= 10) {
                            to60sec[i] = "0" + (i - 1);
                        }
                        else {
                            to60sec[i] = String.valueOf(i - 1);
                        }
                    }

                    final ArrayAdapter<String> minutesAdapter = new ArrayAdapter<String>(RecordsActivity.this, android.R.layout.simple_spinner_dropdown_item, to60min);
                    minutes.setAdapter(minutesAdapter);
                    final ArrayAdapter<String> secondsAdapter = new ArrayAdapter<String>(RecordsActivity.this, android.R.layout.simple_spinner_dropdown_item, to60sec);
                    seconds.setAdapter(secondsAdapter);

                    String[] to100 = new String[101];
                    to100[0] = "Millis";
                    for (int i = 1; i <  to100.length; i++) {
                        if (i <= 10) {
                            to100[i] = "0" + (i - 1);
                        }
                        else {
                            to100[i] = String.valueOf(i - 1);
                        }
                    }

                    final ArrayAdapter<String> millisAdapter = new ArrayAdapter<String>(RecordsActivity.this, android.R.layout.simple_spinner_dropdown_item, to100);
                    millis.setAdapter(millisAdapter);

                    minutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            times[0] = minutesAdapter.getItem(position);
                            displayPreview.setText(times[0] + ":" + times[1] + ":" + times[2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    seconds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            times[1] = secondsAdapter.getItem(position);
                            displayPreview.setText(times[0] + ":" + times[1] + ":" + times[2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    millis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            times[2] = millisAdapter.getItem(position);
                            displayPreview.setText(times[0] + ":" + times[1] + ":" + times[2]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    alertDialog.show();
                } else {
                    Toast.makeText(RecordsActivity.this, "Please choose a heat", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int getResourceByStyle(String favoriteHeat) {
        // For getting the asset we want to display by the swimming style
        if (favoriteHeat.toLowerCase().contains("fly")) {
            return R.drawable.fly_vector;
        }

        else if (favoriteHeat.toLowerCase().contains("breaststroke")) {
            return R.drawable.breast_vector;
        }

        else if (favoriteHeat.toLowerCase().contains("backstroke")) {
            return R.drawable.back_vector;
        }

        return R.drawable.freestyle_vector;
    }
}
