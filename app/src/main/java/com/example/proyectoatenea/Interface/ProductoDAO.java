package com.example.proyectoatenea.Interface;

import androidx.lifecycle.LiveData;

import com.example.proyectoatenea.model.Product;

import java.util.List;

public interface ProductoDAO {
    void agregarProducto(Product product);
    void actualizarProducto(Product product);
    void eliminarProducto(int imageResourceId);

    LiveData<List<Product>> obtenerTodosLosProductos();
}