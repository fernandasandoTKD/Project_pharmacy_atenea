package com.example.proyectoatenea.model;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class PoductViewModel extends ViewModel {
    private List<Product> products = new ArrayList<>();

    public void agregarProduct (String name , double price ){
        products.add(new Product(name,price));
    }
    public List<Product> getProducts(){
        return products;
    }
}
