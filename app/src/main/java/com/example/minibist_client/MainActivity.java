package com.example.minibist_client;

import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.minibist_client.model.Task;
import com.example.minibist_client.serializer.LoginContent;
import com.example.minibist_client.serializer.LoginSerializer;
import com.google.gson.Gson;

public class MainActivity extends Activity {

    private Socket socket;

    private static final int SERVERPORT = 5000;
    private static final String SERVER_IP = "10.0.2.2";
    private TextView text;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogin = findViewById(R.id.loginButton);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        Button btnSignup = findViewById(R.id.signupButton);
        btnSignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }
        });
        /*
        * String str = text.getText().toString();
                Gson gson = new Gson();
                LoginContent loginContent = new LoginContent("email@gmail.com", "passwordEmail");
                LoginSerializer loginSerializer = new LoginSerializer(gson.toJson(loginContent));
                String s = gson.toJson(loginSerializer);
                Task t = gson.fromJson(s, Task.class);
                new SocketHandler(gson.toJson(loginSerializer)).execute();
        * */

    }
}