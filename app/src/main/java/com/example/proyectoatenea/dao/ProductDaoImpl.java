package com.example.proyectoatenea.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyectoatenea.Interface.ProductoDAO;
import com.example.proyectoatenea.model.Product;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductoDAO {
    private FirebaseFirestore db;
    @Override
    public void agregarProducto(Product product) {
        db.collection("productos").add(product);
    }

    @Override
    public void actualizarProducto(Product product) {
        db.collection("productos").document(product.getImageResourceId()).set(product);
    }

    @Override
    public void eliminarProducto(int imageResourceId) {
        db.collection("productos").document(String.valueOf(imageResourceId)).delete();
    }

    @Override
    public LiveData<List<Product>> obtenerTodosLosProductos() {
        MutableLiveData<List<Product>> productosLiveData = new MutableLiveData<>();
        db.collection("productos").addSnapshotListener((snapshots,e) ->{
            if(e != null){
                return;
            }
            List<Product> productos = new ArrayList<>();
            for(DocumentSnapshot snapshot: snapshots){
                Product product = snapshot.toObject(Product.class);
                productos.add(product);
            }
            productosLiveData.setValue(productos);
        });
        return productosLiveData;
    }
}
