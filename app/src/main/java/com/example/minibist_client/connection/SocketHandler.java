package com.example.minibist_client.connection;

import android.os.AsyncTask;

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

public class SocketHandler extends AsyncTask<Void, Void, JSONObject> {
    private Socket socket;
    BufferedReader reader;
    BufferedWriter writer;
    private String message;
    Gson gson;

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
            System.out.println(serverResponse);
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
    }
}

