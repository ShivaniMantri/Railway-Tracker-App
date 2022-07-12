package com.example.railway_trackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getPnrStatus extends AppCompatActivity {

    String pnrNo, train_num, train_name, date, from, to, reserved, boarding, platform;
    String tclass, p1, p2, status1, status2, cstatus1, cstatus2, coach1, coach2;

    TextView pnrnum, trainnum, trainName, b_date, tFrom, tTo, reserved_upto, boarding_pt, pltfm;
    TextView t_class, p_1, p_2, status_1, status_2, cstatus_1, cstatus_2, c1, c2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_pnr_status);

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#666ad1"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        pnrnum = findViewById(R.id.pnr_no);
        trainnum = findViewById(R.id.trainNumber);
        trainName = findViewById(R.id.trainName);
        b_date = findViewById(R.id.date_boarding);
        tFrom = findViewById(R.id.from);
        tTo = findViewById(R.id.to);
        reserved_upto = findViewById(R.id.reserved);
        boarding_pt = findViewById(R.id.boarding_point);
        pltfm = findViewById(R.id.platform);
        t_class = findViewById(R.id.class_name);
        p_1 = findViewById(R.id.p1);
        p_2 = findViewById(R.id.p2);
        status_1 = findViewById(R.id.status1);
        status_2 = findViewById(R.id.status2);
        cstatus_1 = findViewById(R.id.cstatus1);
        cstatus_2 = findViewById(R.id.cstatus2);
        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);


        Intent intent = getIntent();
        String pnrNumber = intent.getStringExtra("pnr_number");
        Log.d(pnrNumber, "pnrNumber");
        try {
            fetchData(pnrNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fetchData(String pnrNumber) throws IOException {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://mocki.io/v1/5e5d18ba-81c2-46e0-ab7f-8e3daaf62892")
                    .get()
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Toast.makeText(getPnrStatus.this, "Error +", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        try {
                            JSONArray jsonArray = new JSONArray(response.body().string());
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            pnrNo = jsonObject.getString("pnr");
                            train_num = jsonObject.getString("trainNum");
                            train_name = jsonObject.getString("trainName");
                            date = jsonObject.getString("date");
                            from = jsonObject.getString("from");
                            to = jsonObject.getString("to");
                            reserved = jsonObject.getString("reserved");
                            boarding = jsonObject.getString("boarding");
                            platform = jsonObject.getString("platform");
                            tclass = jsonObject.getString("class");
                            p1 = jsonObject.getString("p1");
                            p2 = jsonObject.getString("p2");
                            status1 = jsonObject.getString("status1");
                            status2 = jsonObject.getString("status2");
                            cstatus1 = jsonObject.getString("cstatus1");
                            cstatus2 = jsonObject.getString("cstatus2");
                            coach1 = jsonObject.getString("coach1");
                            coach2 = jsonObject.getString("coach2");
                            Log.d(status1, "status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                pnrnum.setText(pnrNo);
                                trainnum.setText(train_num);
                                trainName.setText(train_name);
                                b_date.setText(date);
                                tFrom.setText(from);
                                tTo.setText(to);
                                reserved_upto.setText(reserved);
                                boarding_pt.setText(boarding);
                                pltfm.setText(platform);
                                t_class.setText(tclass);
                                p_1.setText(p1);
                                p_2.setText(p2);
                                status_1.setText(status1);
                                status_2.setText(status2);
                                cstatus_1.setText(cstatus1);
                                cstatus_2.setText(cstatus2);
                                c1.setText(coach1);
                                c2.setText(coach2);
                            }
                        });
                    }
                }
            });
        }
    }

