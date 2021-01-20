package com.example.minibist_client;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    EditText etFirstName, etLastName, etEmail, etPassword, etRepeatPassword;
    final int MIN_PASSWORD_LENGTH = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        viewInitializations();
    }

    void viewInitializations() {
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etRepeatPassword = findViewById(R.id.et_repeat_password);

        // To show back button in actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // Checking if the input in form is valid
    boolean validateInput() {
        if (etFirstName.getText().toString().equals("")) {
            etFirstName.setError("Please Enter First Name");
            return false;
        }
        if (etLastName.getText().toString().equals("")) {
            etLastName.setError("Please Enter Last Name");
            return false;
        }
        if (etEmail.getText().toString().equals("")) {
            etEmail.setError("Please Enter Email");
            return false;
        }
        if (etPassword.getText().toString().equals("")) {
            etPassword.setError("Please Enter Password");
            return false;
        }
        if (etRepeatPassword.getText().toString().equals("")) {
            etRepeatPassword.setError("Please Enter Repeat Password");
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

        // Checking if repeat password is same
        if (!etPassword.getText().toString().equals(etRepeatPassword.getText().toString())) {
            etRepeatPassword.setError("Password does not match");
            return false;
        }
        return true;
    }

    boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Hook Click Event

    public void performSignUp (View v) {
        if (validateInput()) {

            // Input is valid, here send data to your server

            String firstName = etFirstName.getText().toString();
            String lastName = etLastName.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String repeatPassword = etRepeatPassword.getText().toString();

            Toast.makeText(this,"Signup Successful",Toast.LENGTH_SHORT).show();
            // Here you can call you API
            Intent signupSuccess = new Intent(this, LoginActivity.class);
            startActivity(signupSuccess);
        }
    }

}