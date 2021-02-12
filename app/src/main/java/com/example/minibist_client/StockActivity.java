package com.example.minibist_client;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.minibist_client.context.AppContext;
import com.example.minibist_client.model.ServerResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

public class StockActivity extends AppCompatActivity {

    Button btnBuy, btnSell;
    EditText edtTextPrice, edtTextAmount, edtStockPrice;
    Spinner stockSpinner;
    final String[] stockNames = { "EREGL", "GARAN", "THYAO", "ARCLK", "TOASO" };
    public static final HashMap<String, Integer> stockPrices = new HashMap<String, Integer>(){
        {
            put("TOASO", 36);
            put("GARAN", 10);
            put("THYAO", 12);
            put("ARCLK", 34);
            put("EREGL", 14);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StockActivity.this, ProfileActivity.class);
        intent.putExtra("FLAG", 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);



        edtTextAmount = findViewById(R.id.editTextAmountValue);
        edtTextPrice = findViewById(R.id.editTextPriceValue);
        edtStockPrice = findViewById(R.id.stockPrice);

        btnBuy = findViewById(R.id.buyButton);
        btnSell = findViewById(R.id.sellButton);

        stockSpinner = findViewById(R.id.stockSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stockNames);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        stockSpinner.setAdapter(adapter);

        stockSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                edtStockPrice.setText(Integer.toString(stockPrices.get(stockSpinner.getSelectedItem().toString())) + " ₺");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()) {
//                    Toast.makeText(getApplicationContext(), "Buy order\n" +
//                            "Amount: " + edtTextAmount.getText() + "\n" +
//                            "Price: "+ edtTextPrice.getText(), Toast.LENGTH_SHORT).show();
                    JSONObject content = new JSONObject();
                    JSONObject message = new JSONObject();
                    try {
                        content.put("amount", edtTextAmount.getText().toString());
                        content.put("price", edtTextPrice.getText().toString());
                        content.put("email", AppContext.email);
                        content.put("stockName", stockSpinner.getSelectedItem().toString());
                        message.put("operation", "buy");
                        message.put("message", content.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    SocketHandler handler = new SocketHandler(message.toString(), true, edtTextPrice.getText().toString(), edtTextAmount.getText().toString());
                    handler.execute();
                }
            }
        });


        btnSell.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(validateInput()){
//                    Toast.makeText(getApplicationContext(), "Sell order\n" +
//                            "Amount: " + edtTextAmount.getText() + "\n" +
//                            "Price: "+ edtTextPrice.getText(), Toast.LENGTH_SHORT).show();

                    JSONObject content = new JSONObject();
                    JSONObject message = new JSONObject();
                    try {
                        content.put("amount", edtTextAmount.getText().toString());
                        content.put("price", edtTextPrice.getText().toString());
                        content.put("email", AppContext.email);
                        content.put("stockName", stockSpinner.getSelectedItem().toString());
                        message.put("operation", "sell");
                        message.put("message", content.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    SocketHandler handler = new SocketHandler(message.toString(), false, edtTextPrice.getText().toString(), edtTextAmount.getText().toString());
                    handler.execute();
                }
            }
        });
    }

    public class SocketHandler extends AsyncTask<Void, Void, JSONObject> {
        private Socket socket;
        BufferedReader reader;
        BufferedWriter writer;
        private String message;
        Gson gson;
        Boolean isBuy;
        Integer price;
        Integer amount;

        private ServerResponse serverResponse = null;

        public SocketHandler(String message, Boolean isBuy, String price, String amount){
            this.message = message;
            this.gson = new Gson();
            this.isBuy = isBuy;
            this.price = Integer.parseInt(price);
            this.amount = Integer.parseInt(amount);
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                InetAddress serverAddr = InetAddress.getByName(AppContext.ip);
                socket = new Socket(serverAddr, AppContext.port);
                writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));
                reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONObject response = null;

            try {
                writer.write(this.message);
                writer.newLine();
                writer.flush();
                String responseStr = reader.readLine();
                ServerResponse serverResponse = this.gson.fromJson(responseStr, ServerResponse.class);
                this.serverResponse = serverResponse;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(JSONObject req) {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (this.serverResponse.getStatus().equals("success")) {
                Toast.makeText(StockActivity.this, "Successfully completed order!", Toast.LENGTH_SHORT).show();
                if(this.isBuy) {
                    AppContext.money -= this.price * this.amount;
                } else {
                    AppContext.money += this.price * this.amount;
                }
                ProfileActivity.moneyAmount.setText("Money : " + Integer.toString(AppContext.money) + "₺");
            } else {
                Toast.makeText(StockActivity.this, this.serverResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        public ServerResponse getServerResponse() {
            return serverResponse;
        }
    }
    boolean validateInput() {

        if (edtTextAmount.getText().toString().equals("")) {
            edtTextAmount.setError("Please Enter Integer Amount");
            return false;
        }
        if (edtTextPrice.getText().toString().equals("")) {
            edtTextPrice.setError("Please Enter Integer Amount");
            return false;
        }
        if(Integer.parseInt(edtTextPrice.getText().toString()) <= 0) {
            edtTextPrice.setError("Please Enter Positive Price");
            return false;
        }
        if(Integer.parseInt(edtTextAmount.getText().toString()) <= 0 ) {
            edtTextAmount.setError("Please Enter Positive Amount");
            return false;
        }
        return true;
    }
}