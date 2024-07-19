package com.example.proyectoatenea.model;

public class Product {
    private int id;
    private String name;
    private double price;
    private String descripcion;


   public Product(){

   }

    public Product(String name, double price, String descripcion, int id) {
        this.name = name;
        this.price = price;
        this.descripcion = descripcion;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

