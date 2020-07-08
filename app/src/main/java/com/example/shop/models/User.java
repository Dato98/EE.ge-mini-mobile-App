package com.example.shop.models;

import java.util.ArrayList;

public class User {
    private String Name;
    private String Surname;
    private String Tel;
    private String UserName;
    private String Password;
    private ArrayList<CreditCard> creditCards;
    private ArrayList<PurchaseProduct> products;

    public User(String name, String surname, String tel, String userName, String password) {
        Name = name;
        Surname = surname;
        Tel = tel;
        UserName = userName;
        Password = password;
        creditCards = new ArrayList<>();
        products = new ArrayList<>();
    }

    public void Edit(String name,String surname,String tel){
        setName(name);
        setSurname(surname);
        setTel(tel);
    }

    public void AddCreditCard(CreditCard card){
        creditCards.add(card);
    }

    public void DeleteCreditCard(CreditCard card){creditCards.remove(card);}
    public CreditCard getCardByNumber(String Number){
        Number = Number.replace("-","");
        for(int i = 0;i<creditCards.size();i++){
            if(creditCards.get(i).getCardNumber().equals(Number))
                return creditCards.get(i);
        }
        return null;
    }
    public void AddPurchaseProduct(PurchaseProduct product){
        products.add(0,product);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public ArrayList<CreditCard> getCreditCards() {
        return creditCards;
    }

    public ArrayList<PurchaseProduct> getProducts() {
        return products;
    }
}
