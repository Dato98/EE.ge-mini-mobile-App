package com.example.shop.models;

public class Product {
    private String Brand;
    private String Name;
    private double Price;
    private String Thumb;

    public Product(String brand, String name, double price, String thumb) {
        Brand = brand;
        Name = name;
        Price = price;
        Thumb = thumb;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getThumb() {
        return Thumb;
    }

    public void setThumb(String thumb) {
        Thumb = thumb;
    }
}
