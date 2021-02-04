package com.example.minibist_client.connection;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.minibist_client.LoginActivity;
import com.example.minibist_client.MainActivity;
import com.example.minibist_client.StockActivity;
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

import lombok.Getter;



