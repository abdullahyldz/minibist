package com.example.minibist_client.serializer;

import java.io.Serializable;

public class LoginSerializer implements Serializable {

    private final String operation = "login";
    private String message;

    public LoginSerializer(String loginContent) {
        this.message = loginContent;
    }
}