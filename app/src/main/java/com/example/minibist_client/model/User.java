package com.example.minibist_client.model;

import java.util.HashMap;

public class User {

    private String fullname;
    private String email;
    private HashMap<String, Integer> ownedStocks;

    public User(String fullname, String email, HashMap<String, Integer> ownedStocks) {
        this.fullname = fullname;
        this.email = email;
        this.ownedStocks = ownedStocks;
    }

    public void setOwnedStocks(HashMap<String, Integer> ownedStocks) {
        this.ownedStocks = ownedStocks;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", ownedStocks=" + ownedStocks +
                '}';
    }
}
