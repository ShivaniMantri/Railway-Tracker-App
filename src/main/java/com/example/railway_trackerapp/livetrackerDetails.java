package com.example.railway_trackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class livetrackerDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livetracker_details);
    }

    public void getlivedata(View view) {
        EditText train_no = (EditText) findViewById(R.id.train_no);
        EditText date = (EditText) findViewById(R.id.date);
        Intent intent = new Intent(livetrackerDetails.this, getLiveData.class);
        intent.putExtra("usertrain_no", train_no.getText().toString());
        intent.putExtra("user_date", date.getText().toString());
        startActivity(intent);
    }
}