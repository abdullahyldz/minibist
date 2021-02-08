package com.example.minibist_client;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minibist_client.context.AppContext;
import com.example.minibist_client.model.ProfileActivity;
import com.example.minibist_client.model.ServerResponse;
import com.example.minibist_client.model.User;
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

public class LoginActivity extends Activity {
    Button loginButton, cancelButton;
    EditText etEmail, etPassword;
    final int MIN_PASSWORD_LENGTH = 6;

    int counter = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText)findViewById(R.id.emailBox);
        etPassword = (EditText)findViewById(R.id.passwordBox);

        loginButton = (Button)findViewById(R.id.loginButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()){
                    JSONObject content = new JSONObject();
                    JSONObject message = new JSONObject();
                    try {
                        content.put("email", etEmail.getText().toString());
                        content.put("password", etPassword.getText().toString());
                        message.put("operation", "login");
                        message.put("message", content.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    SocketHandler handler = new SocketHandler(message.toString());
                    handler.execute();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
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
            if(this.serverResponse!=null) {
                if ( this.serverResponse.getStatus().equals("success")) {
                    Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    AppContext.email = etEmail.getText().toString();
                    Intent loginSuccess = new Intent(LoginActivity.this, ProfileActivity.class);
                    LoginActivity.this.startActivity(loginSuccess);
                    AppContext.email = etEmail.getText().toString();
                } else {
                    Toast.makeText(LoginActivity.this, this.serverResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        }

        public ServerResponse getServerResponse() {
            return serverResponse;
        }
    }

    // Checking if the input in form is valid
    boolean validateInput() {

        if (etEmail.getText().toString().equals("")) {
            etEmail.setError("Please Enter Email");
            return false;
        }
        if (etPassword.getText().toString().equals("")) {
            etPassword.setError("Please Enter Password");
            return false;
        }
        // checking the proper email format
        if (!isEmailValid(etEmail.getText().toString())) {
            etEmail.setError("Please Enter Valid Email");
            return false;
        }
        // checking minimum password Length
        if (etPassword.getText().length() < MIN_PASSWORD_LENGTH) {
            etPassword.setError("Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters");
            return false;
        }
        return true;
    }

    boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}