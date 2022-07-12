package com.example.railway_trackerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class stationDetails extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.station_details);
    }

    public void getStations(View view) {
        EditText stationName = (EditText) findViewById(R.id.station_name);
        Intent intent = new Intent(stationDetails.this, getStations.class);
        intent.putExtra("userstation_name", stationName.getText().toString());
        startActivity(intent);
    }
}
