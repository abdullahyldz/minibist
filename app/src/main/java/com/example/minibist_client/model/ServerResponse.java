package com.example.minibist_client.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServerResponse {

    String status;
    String message;

    public String getStatus() {
        return this.status;
    }

    public String getMessage() {
        return message;
    }
}
