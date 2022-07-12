package com.example.railway_trackerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class getStations extends AppCompatActivity {
    private ListView lv;
    String stationCode;
    String station_Name;
    ArrayList stationList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_stations);
        
        lv = findViewById(R.id.list_view);
        Intent intent = getIntent();
        String stationName = intent.getStringExtra("userstation_name");
        fetchData(stationName);
    }

    private void fetchData(String stationName) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        String value = "{\r\"search\": \""+stationName+"\"\r}";
    RequestBody body = RequestBody.create(mediaType, value);
    Request request = new Request.Builder()
            .url("https://rstations.p.rapidapi.com/")
            .post(body)
            .addHeader("content-type", "application/json")
            .addHeader("Content-Type", "application/json")
            .addHeader("X-RapidAPI-Host", "rstations.p.rapidapi.com")
            .addHeader("X-RapidAPI-Key", "741c6db7d7mshf2ce1d35052684ap1e31d1jsn14fa42624b5a")
            .build();

    client.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            Toast.makeText(getStations.this, "Error +", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            if (response.isSuccessful()) {
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONArray jsonArray1 = jsonArray.getJSONArray(0);
                        HashMap<String ,String> stations = new HashMap<>();
                        stationCode = (String) jsonArray1.get(0);
                        station_Name = (String) jsonArray1.get(1);
                        stations.put("station_code", stationCode);
                        stations.put("station_name", station_Name);
                        stationList.add(stations);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ListAdapter listAdapter = new SimpleAdapter(
                                getStations.this,
                                stationList,
                                R.layout.stationitem_layout,
                                new String[] {"station_code", "station_name"},
                                new int[]{R.id.stationCode, R.id.stationName});
                        lv.setAdapter(listAdapter);
                    }
                });
            }
        }
    });
    }
}
