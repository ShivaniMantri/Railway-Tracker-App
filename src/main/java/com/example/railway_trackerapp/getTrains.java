package com.example.railway_trackerapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class getTrains extends AppCompatActivity {
    private ListView lv;
    Integer train_num;
    String name, train_from, train_to;
    String id, to_id, from_id, arriveTime, departTime;
    JSONObject data, days_data;
    ArrayList days = new ArrayList();
    ArrayList trainList = new ArrayList<>();
    ArrayList classes = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_trains);

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#666ad1"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        lv = findViewById(R.id.listView);
        classes.add("3A");
        classes.add("2A");
        classes.add("1A");
        Intent intent = getIntent();
        String trainName = intent.getStringExtra("usertrain_name");
        try {
            fetchData(trainName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fetchData(String trainName) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        String value = "{\r\"search\": \""+trainName+"\"\r }";

        RequestBody body = RequestBody.create(mediaType, value);
        Request request = new Request.Builder()
                .url("https://trains.p.rapidapi.com/")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("X-RapidAPI-Host", "trains.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "741c6db7d7mshf2ce1d35052684ap1e31d1jsn14fa42624b5a")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(getTrains.this, "Error +", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            HashMap<String ,String> trains = new HashMap<>();
                            HashMap<ArrayList, String> trainData = new HashMap<>();
                            train_num = jsonObject.getInt("train_num");
                            name = jsonObject.getString("name");
                            train_from = jsonObject.getString("train_from");
                            train_to = jsonObject.getString("train_to");
                            data = jsonObject.getJSONObject("data");
                            days_data = data.getJSONObject("days");
                            days = getWorkingDays(days_data);
                            id = data.getString("id");
                            to_id = data.getString("to_id");
                            from_id = data.getString("from_id");
                            arriveTime = data.getString("arriveTime");
                            departTime = data.getString("departTime");
                            trains.put("train_num", String.valueOf(train_num));
                            trains.put("name", name);
                            trains.put("train_from", train_from);
                            trains.put("train_to", train_to);
                            trains.put("id", id);
                            trains.put("to_id", to_id);
                            trains.put("from_id", from_id);
                            trains.put("arriveTime", arriveTime);
                            trains.put("departTime", departTime);
                            trainData.put(days, "days");
                            trainData.put(classes, "classes");
                            trainList.add(trains);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ListAdapter listAdapter = new SimpleAdapter(
                                    getTrains.this,
                                    trainList,
                                    R.layout.trainitem_layout,
                                    new String[] {"train_num", "name", "train_from", "train_to", "id", "to_id", "from_id", "arriveTime", "departTime"},
                                    new int[]{R.id.trainNum, R.id.Name, R.id.trainFrom, R.id.trainTo, R.id.id, R.id.to_id, R.id.from_id, R.id.arriveTime, R.id.departTime});
                            lv.setAdapter(listAdapter);
                        }
                    });
                }
            }
        });
    }


    private ArrayList getWorkingDays(JSONObject days_data) throws JSONException {
        ArrayList days = new ArrayList();
        if (days_data.getInt("Fri") == 1) {
            days.add("Fri");
        }
        if (days_data.getInt("Mon") == 1) {
            days.add("Mon");
        }
        if (days_data.getInt("Sat") == 1) {
            days.add("Sat");
        }
        if (days_data.getInt("Sun") == 1) {
            days.add("Sun");
        }
        if (days_data.getInt("Thu") == 1) {
            days.add("Thu");
        }
        if (days_data.getInt("Tue") == 1) {
            days.add("Tue");
        }
        if (days_data.getInt("Wed") == 1) {
            days.add("Wed");
        }
        return days;
    }
}
