package com.example.proyectoatenea.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyectoatenea.model.Product;

import java.util.ArrayList;

public class DaoProduct {
    SQLiteDatabase cx;
    ArrayList<Product> lista;
    Product p;
    Context ct;
    String nombreBD = "BDrogueria";
    String tabla = "create table if not exists product(id integer primary key autoincrement, name text, price real, descripcion text)"

    public DaoProduct(Context ct) {
        this.ct = ct;
        cx.p.openOrCreateDatabase(nombreBD, Context.MODE_WORLD_WRITEABLE, null);
        cx.execSQL(tabla);
    }

    public boolean insertar(Product p){
        return true;
    }

    public boolean eliminar(int id){
        return true;
    }

    public boolean editar(Product p){
        return true;
    }
    public ArrayList<Product> verTodos(){
        return lista;
    }

    public Product verUno(int id){
        return p;
    }
}
