package com.example.minibist_client.context;

import com.example.minibist_client.model.User;

import lombok.Data;

@Data
public class AppContext {

    public static String email;
    public static final Integer port = 8080;
    public static final String ip = "3.250.108.35"; // ec2 : 3.250.108.35
    public static Integer money = 100;
    // 10.0.2.2 InetAddress serverAddr = InetAddress.getByName("52.86.12.80"); // 52.86.12.80stormy-wave-53190.herokuapp.com
    // 52.86.12.80
}
