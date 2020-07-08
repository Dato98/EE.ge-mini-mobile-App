package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shop.helpers.ErrorManage;
import com.example.shop.models.User;
import com.example.shop.repositories.implementation.UserRepository;
import com.example.shop.repositories.interfaces.IUserRepository;

public class Registration extends AppCompatActivity {
    Button btnRegister;
    EditText txtName,txtSurName,txtTel,txtUserName,txtPassword,txtRepPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        btnRegister = findViewById(R.id.btn_register);
        txtName = findViewById(R.id.txtUserName);
        txtSurName = findViewById(R.id.txtUserSurname);
        txtTel = findViewById(R.id.txtUserTel);
        txtUserName = findViewById(R.id.txtUserNick);
        txtPassword = findViewById(R.id.txtPassword);
        txtRepPass = findViewById(R.id.txtRepPassword);
    }

    public void Register(View view) {
        ErrorManage errorManage = new ErrorManage();
        if(txtName.getText().length() != 0 && txtSurName.getText().length() != 0 && txtTel.getText().length() != 0 && txtUserName.getText().length() != 0 && txtPassword.getText().length() !=0 && txtRepPass.getText().length() != 0){
            if(txtPassword.getText().toString().equals(txtRepPass.getText().toString())){
                User user = new User(txtName.getText().toString(),txtSurName.getText().toString(),txtTel.getText().toString(),txtUserName.getText().toString(),txtPassword.getText().toString());
                IUserRepository userRepository = new UserRepository();
                String res = userRepository.addUser(user,getApplicationContext());
                if(res.equals("Success")){
                    Toast.makeText(this,"მომხმარებელი წარმატებით დარეგისტრირდა",Toast.LENGTH_LONG);
                    finish();
                }else{
                    errorManage.AddError("დაფიქსირდა შეცდომა");
                }
            }else{
                errorManage.AddError("პაროლები არ ემთხვევა ერთმანეთს");
            }
        }else{
            errorManage.AddError("შეავსეთ ყველა ველი");
        }
        Toast.makeText(this,errorManage.getErrorData(),Toast.LENGTH_LONG).show();
    }
}
