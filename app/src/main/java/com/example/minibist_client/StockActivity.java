package com.example.minibist_client;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StockActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        Button btnBuy, btnSell;
        EditText edtTextPrice, edtTextAmount;

        edtTextAmount = findViewById(R.id.editTextAmountValue);
        edtTextPrice = findViewById(R.id.editTextPriceValue);

        btnBuy = findViewById(R.id.buyButton);
        btnSell = findViewById(R.id.sellButton);

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Buy order\n" +
                        "Amount: " + edtTextAmount.getText() + "\n" +
                        "Price: "+ edtTextPrice.getText(), Toast.LENGTH_SHORT).show();
            }
        });


        btnSell.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Sell order\n" +
                        "Amount: " + edtTextAmount.getText() + "\n" +
                        "Price: "+ edtTextPrice.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}