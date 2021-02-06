package com.example.minibist_client;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
                JSONObject content = new JSONObject();
                JSONObject message = new JSONObject();
                try {
                    content.put("amount", edtTextAmount.getText().toString());
                    content.put("price", edtTextPrice.getText().toString());
                    message.put("operation", "buy");
                    message.put("message", content.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                SocketHandler handler = new SocketHandler(message.toString());
                handler.execute();
            }
        });


        btnSell.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Sell order\n" +
                        "Amount: " + edtTextAmount.getText() + "\n" +
                        "Price: "+ edtTextPrice.getText(), Toast.LENGTH_SHORT).show();

                JSONObject content = new JSONObject();
                JSONObject message = new JSONObject();
                try {
                    content.put("amount", edtTextAmount.getText().toString());
                    content.put("price", edtTextPrice.getText().toString());
                    message.put("operation", "sell");
                    message.put("message", content.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                SocketHandler handler = new SocketHandler(message.toString());
                handler.execute();
            }
        });
    }

    public class SocketHandler extends AsyncTask<Void, Void, JSONObject> {
        private Socket socket;
        BufferedReader reader;
        BufferedWriter writer;
        private String message;
        Gson gson;
        private ServerResponse serverResponse = null;

        public SocketHandler(String message){
            this.message = message;
            this.gson = new Gson();
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                InetAddress serverAddr = InetAddress.getByName("10.0.2.2");
                socket = new Socket(serverAddr, 6666);
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
                Toast.makeText(StockActivity.this, "Successfully sent your order!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(StockActivity.this, this.serverResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        public ServerResponse getServerResponse() {
            return serverResponse;
        }
    }
}