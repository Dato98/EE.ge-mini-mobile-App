package com.example.shop.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop.R;
import com.example.shop.models.User;
import com.example.shop.repositories.implementation.UserRepository;
import com.example.shop.repositories.interfaces.IUserRepository;

/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {
    private User user;
    private TextView lblName,lblSname,lblTel;
    View view;
    private Button btnSaveSettings;
    private EditText txtNewPass,txtRepPass;
    IUserRepository repository;
    public Settings(User user) {
        this.user = user;
        repository = new UserRepository();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        lblName = view.findViewById(R.id.lblName);
        lblSname = view.findViewById(R.id.lblSname);
        lblTel = view.findViewById(R.id.lblTel);
        btnSaveSettings = view.findViewById(R.id.btn_saveSettings);
        txtNewPass = view.findViewById(R.id.txtSettingsPassword);
        txtRepPass = view.findViewById(R.id.txtSettingsRepPass);

        lblName.setText(user.getName());
        lblSname.setText(user.getSurname());
        lblTel.setText(user.getTel());

        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNewPass.getText().toString().equals(txtRepPass.getText().toString())) {
                    SaveSettings(txtNewPass.getText().toString());
                }else
                    Toast.makeText(getActivity(),"პაროლები ერთმანეთს არ ემთხვევა",Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }


    private void SaveSettings(String newPass){
        String res = repository.editUser(user,newPass,getActivity().getApplicationContext());
        if(res.equals("Success")) {
            Toast.makeText(getActivity(),"პაროლი შეიცვალა",Toast.LENGTH_LONG).show();
            txtRepPass.setText("");
            txtNewPass.setText("");
        }else
            Toast.makeText(getActivity(),"დაფიქსირდა შეცდომა",Toast.LENGTH_LONG).show();
    }
}
