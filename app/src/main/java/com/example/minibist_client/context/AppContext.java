package com.example.minibist_client.context;

import com.example.minibist_client.model.User;

import lombok.Data;

@Data
public class AppContext {

    public static String email;
    public static final Integer port = 80;
    public static final String ip = "10.0.2.2";
    // 10.0.2.2 InetAddress serverAddr = InetAddress.getByName("52.86.12.80"); // 52.86.12.80afternoon-dawn-26859.herokuapp.com
    // 52.86.12.80
}
