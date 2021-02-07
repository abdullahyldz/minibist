package com.example.minibist_client.model;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.minibist_client.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}