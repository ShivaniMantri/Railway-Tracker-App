package com.example.railway_trackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class getLiveData extends AppCompatActivity {
    
    private ListView lv;
    TextView livedata;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_live_data);
    
        //lv = findViewById(R.id.list_live_view);
        webView = (WebView) findViewById(R.id.web_View);
        webView.loadUrl("https://www.railmitra.com/live-train-running-status");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        
        Intent intent = getIntent();
        String train_no = intent.getStringExtra("usertrain_no");
        String date = intent.getStringExtra("user_date");
    }

}