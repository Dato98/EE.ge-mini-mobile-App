package com.example.shop.models;

public class CreditCard {
    private String CardNumber;
    private String ExpireMonth;
    private String ExpireYear;
    private String CVV;

    public CreditCard(String cardNumber, String expireMonth, String expireYear, String CVV) {
        CardNumber = cardNumber;
        ExpireMonth = expireMonth;
        ExpireYear = expireYear;
        this.CVV = CVV;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getExpireMonth() {
        return ExpireMonth;
    }

    public void setExpireMonth(String expireMonth) {
        ExpireMonth = expireMonth;
    }

    public String getExpireYear() {
        return ExpireYear;
    }

    public void setExpireYear(String expireYear) {
        ExpireYear = expireYear;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }
}
