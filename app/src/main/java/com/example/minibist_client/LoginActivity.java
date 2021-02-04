package com.example.minibist_client;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minibist_client.model.ServerResponse;
import com.example.minibist_client.model.Task;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class LoginActivity extends Activity {
    Button b1,b2;
    EditText ed1,ed2;

    int counter = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed1 = (EditText)findViewById(R.id.emailBox);
        ed2 = (EditText)findViewById(R.id.passwordBox);

        b1 = (Button)findViewById(R.id.loginButton);
        b2 = (Button)findViewById(R.id.cancelButton);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed1.getText().toString().equals("admin") &&
                        ed2.getText().toString().equals("admin")) {

                    JSONObject content = new JSONObject();
                    JSONObject message = new JSONObject();
                    try {
                        content.put("email", ed1.getText().toString());
                        content.put("password", ed2.getText().toString());
                        message.put("operation", "login");
                        message.put("message", content.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    SocketHandler handler = new SocketHandler(message.toString());
                    handler.execute();

                    Toast.makeText(getApplicationContext(), "Please wait...",Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "A problem occurred. Please try again.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                Intent loginSuccess = new Intent(LoginActivity.this, StockActivity.class);
                LoginActivity.this.startActivity(loginSuccess);
            } else {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

        public ServerResponse getServerResponse() {
            return serverResponse;
        }
    }
}