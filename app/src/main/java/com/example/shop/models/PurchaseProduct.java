package com.example.shop.models;

public class PurchaseProduct {
    private Product product;
    private String PurchaseDate;

    public PurchaseProduct(Product product, String purchaseDate) {
        this.product = product;
        PurchaseDate = purchaseDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public String getPurchaseDate() {
        return PurchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        PurchaseDate = purchaseDate;
    }
}
