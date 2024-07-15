package com.example.proyectoatenea.model;

public class Product {
    private String name;
    private String price;
    private int imageResourceId;

    public Product(String name, String description, int imageResourceId) {
        this.name = name;
        this.price = description;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}

