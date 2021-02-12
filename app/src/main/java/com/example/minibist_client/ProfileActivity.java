package com.example.minibist_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.minibist_client.context.AppContext;

public class ProfileActivity extends AppCompatActivity {
    Button btnStartStockActivity;
    public static EditText moneyAmount ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btnStartStockActivity = findViewById(R.id.startStockActivityButton);
        moneyAmount = findViewById(R.id.moneyAmount);
        moneyAmount.setText("Money : " + Integer.toString(AppContext.money) + "₺");

        btnStartStockActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, StockActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}