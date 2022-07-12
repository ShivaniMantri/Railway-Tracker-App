package com.example.railway_trackerapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PnrDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnr_details);

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#666ad1"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
    }

    public void gopnrDetails(View view) {
        EditText pnrNumber = (EditText) findViewById(R.id.pnr_no);
        Log.d(pnrNumber.getText().toString(), "PNR nuuuuuum");
        if (pnrNumber.getText().toString().equals("4543508248")) {
            Intent intent = new Intent(PnrDetails.this, getPnrStatus.class);
            intent.putExtra("pnr_number", pnrNumber.getText().toString());
            startActivity(intent);
        } else {
            Toast.makeText(PnrDetails.this, "Error while fetching PNR details", Toast.LENGTH_SHORT).show();
        }

    }
}