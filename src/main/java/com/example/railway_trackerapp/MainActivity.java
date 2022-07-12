package com.example.railway_trackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextView verifyMessage;
    Button verify;
    ImageView lock;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        lock = findViewById(R.id.lock);
        lock.setRotation(20);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#666ad1"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
        }

        firebaseAuth = FirebaseAuth.getInstance();
        verifyMessage = findViewById(R.id.verify_status);
        verify = findViewById(R.id.verify);

        if (!firebaseAuth.getCurrentUser().isEmailVerified()) {
            verifyMessage.setVisibility(View.VISIBLE);
            verify.setVisibility(View.VISIBLE);
            lock.setVisibility(View.VISIBLE);
        }

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "Verification Email is sent", Toast.LENGTH_SHORT).show();
                        verifyMessage.setVisibility(View.GONE);
                        verify.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    public void gotoPNR(View view) {
        Intent intent = new Intent(MainActivity.this, PnrDetails.class);
        startActivity(intent);
    }

    public void getTrainDetails(View view) {
        Intent intent = new Intent(MainActivity.this, TrainDetails.class);
        startActivity(intent);
    }

    public void goStation(View view) {
        Intent intent = new Intent(MainActivity.this, stationDetails.class);
        startActivity(intent);
    }

    public void goSignin(View view) {
        Intent intent = new Intent(MainActivity.this, sign_in.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this, sign_in.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void livetrack(View view) {
        if (!firebaseAuth.getCurrentUser().isEmailVerified()) {
            Toast.makeText(MainActivity.this, "You need to be verified to access this module", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MainActivity.this, getLiveData.class);
            startActivity(intent);
        }
    }

}