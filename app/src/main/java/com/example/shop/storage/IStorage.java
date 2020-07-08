package com.example.shop.storage;

import android.content.Context;

import com.example.shop.models.User;

public interface IStorage {
    String Save(Context context,String request ,User user);
    User get(Context context,String Username,String Password);
}
