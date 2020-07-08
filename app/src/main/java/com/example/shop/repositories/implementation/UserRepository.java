package com.example.shop.repositories.implementation;

import android.content.Context;

import com.example.shop.models.CreditCard;
import com.example.shop.models.PurchaseProduct;
import com.example.shop.models.User;
import com.example.shop.repositories.interfaces.IUserRepository;
import com.example.shop.storage.IStorage;
import com.example.shop.storage.StorageSharedPreference;

public class UserRepository implements IUserRepository {
    private IStorage storage;

    public UserRepository(){
        storage = new StorageSharedPreference();
    }

    @Override
    public String addUser(User user, Context context) {
        return storage.Save(context,"Add",user);
    }

    @Override
    public String editUser(User user,String password, Context context) {
        user.setPassword(password);
        return storage.Save(context,"Save",user);
    }

    @Override
    public void addCreditCard(User user, CreditCard card, Context context) {
        user.AddCreditCard(card);
        storage.Save(context,"Save",user);
    }

    @Override
    public void addPurchaseProduct(User user, PurchaseProduct product, Context context) {
        //User syncUser = getUser(user.getUserName(),user.getPassword(),context);
        user.AddPurchaseProduct(product);
        storage.Save(context,"Save",user);
    }

    @Override
    public void Save(User user, Context context) {
        storage.Save(context,"Save",user);
    }

    @Override
    public User getUser(String userName, String passWord, Context context) {
        return storage.get(context,userName,passWord);
    }
}
