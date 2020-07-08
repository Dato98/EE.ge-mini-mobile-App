package com.example.shop.repositories.interfaces;

import android.content.Context;

import com.example.shop.models.CreditCard;
import com.example.shop.models.PurchaseProduct;
import com.example.shop.models.User;

public interface IUserRepository {
    String addUser(User user, Context context);
    String editUser(User user,String password, Context context);
    void addCreditCard(User user, CreditCard card,Context context);
    void addPurchaseProduct(User user, PurchaseProduct product,Context context);
    void Save(User user,Context context);
    User getUser(String userName,String passWord, Context context);
}
