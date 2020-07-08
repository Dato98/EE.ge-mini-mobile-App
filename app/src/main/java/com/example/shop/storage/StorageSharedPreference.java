package com.example.shop.storage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.shop.models.User;
import com.google.gson.Gson;

public class StorageSharedPreference implements IStorage {

    @Override
    public String Save(Context context,String request,User user) {
        try {
            SharedPreferences sharedPreferences = getInstance(context);
            if (request.equals("Add"))
                if (sharedPreferences.contains(user.getUserName()))
                    return "მომხმარებელი უკვე არსებობს";

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(user.getUserName(), new Gson().toJson(user));
            editor.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
        return "Success";
    }

    @Override
    public User get(Context context, String Username, String Password) {
        SharedPreferences sharedPreferences = getInstance(context);
        User user = new Gson().fromJson(sharedPreferences.getString(Username,null),User.class);
        if(user != null && user.getPassword().equals(Password))
            return user;
        return null;
    }

    private SharedPreferences getInstance(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("users", Context.MODE_PRIVATE);
        return sharedPref;
    }
}
