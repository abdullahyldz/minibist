package com.example.minibist_client.model;

import com.google.gson.JsonObject;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Task implements Serializable {

    private String operation;
    private String message;

    public Task(String operation, String message){
        this.message = message;
        this.operation = operation;
    }
}
