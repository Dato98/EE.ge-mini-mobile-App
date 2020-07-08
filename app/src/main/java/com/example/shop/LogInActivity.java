package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shop.models.User;
import com.example.shop.repositories.implementation.UserRepository;
import com.example.shop.repositories.interfaces.IUserRepository;
import com.google.gson.Gson;

import java.io.Serializable;

public class LogInActivity extends AppCompatActivity {
    Button btnLogIn;
    EditText txtUserName,txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        btnLogIn = findViewById(R.id.btn_login);
        txtUserName = findViewById(R.id.txtLoginUsername);
        txtPassword = findViewById(R.id.txtLoginPassword);
    }



    public void ToRegistration(View view) {
        Intent intent = new Intent(this,Registration.class);
        startActivity(intent);
    }

    public void Login(View view) {
        User user = logUser(txtUserName.getText().toString(),txtPassword.getText().toString());
        if(user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user", new Gson().toJson(user));
            startActivity(intent);
        }else
            Toast.makeText(this,"არასწორი სახელი ან პაროლი",Toast.LENGTH_LONG).show();
    }

    private User logUser(String Username,String Password){
        IUserRepository userRepository = new UserRepository();
        User user = userRepository.getUser(Username,Password,getApplicationContext());
        return user;
    }
}
