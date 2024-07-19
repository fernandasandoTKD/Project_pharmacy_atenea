package com.example.proyectoatenea.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.proyectoatenea.R;
import com.example.proyectoatenea.dao.DaoProduct;
import com.example.proyectoatenea.model.Product;

import java.util.ArrayList;

public class ProductoAdapter extends BaseAdapter {
    ArrayList<Product> lista;
    DaoProduct daoProduct;
    Product product;
    Activity activity;

    public ProductoAdapter(ArrayList<Product> _lista, Activity _activity, DaoProduct _daoProduct){
        this.lista = _lista;
        this.daoProduct = _daoProduct;
        this.activity = _activity;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {

        product = lista.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        product = lista.get(i);
        return product.getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;
        if(v!null){
            LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=li.inflate(R.layout.item_product);
        }
        return v;
        }
}
