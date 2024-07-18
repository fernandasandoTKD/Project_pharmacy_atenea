package com.example.proyectoatenea.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.proyectoatenea.model.Product;

import java.util.ArrayList;
import java.util.List;


public class ProductDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PRODUCTS ="products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";

    public ProductDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       String createTableSQL = "CREATE TABLE " + TABLE_PRODUCTS +"("+
               COLUMN_ID +"INTEGER PRIMARY KEY AUTOINCREMENT, " +
               COLUMN_NAME +"TEXT,"+
               COLUMN_PRICE +"REAL)";
        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }
    //verificar el stock en la base de datos
    public void  addProduct(Product product) {
        if (productExists(product.getId())){
            Log.i("ProductDatabaseHelper", "Product con ID" +product.getId() );
        }
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, product.getId());
            values.put(COLUMN_NAME, product.getName());
            values.put(COLUMN_PRICE, product.getPrice());
            db.insert(TABLE_PRODUCTS, null, values);
        }catch (Exception e) {
            Log.e("ProductDatabaseHelper", "Producto agotado",e);
        }finally {
            if (db != null){
                db.close();
            }
        }
    }
    // Método para obtener todos los productos de la base de datos
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int priceIndex = cursor.getColumnIndex(COLUMN_PRICE);

            if (idIndex != -1 && nameIndex != -1 && priceIndex != -1) {
                do {
                    String id = cursor.getString(idIndex);
                    String name = cursor.getString(nameIndex);
                    double price = cursor.getDouble(priceIndex);
                    products.add(new Product(id, name, priceIndex));
                } while (cursor.moveToNext());
            } else {
                Log.e("ProductDatabaseHelper", "Columnas no encontradas.");
            }
        }

        // Cerrar el cursor y la base de datos
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return products;
    }

    public void updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_PRICE, product.getPrice());

        int rowsUpdated = db.update(TABLE_PRODUCTS, values, COLUMN_ID + "=?", new String[]{String.valueOf(product.getId())});
        db.close();
        if (rowsUpdated == 0) {
            Log.e("ProductDatabaseHelper", "No se actualizó ninguna fila. ID de producto inválido.");
        }
    }

    public void deleteProduct(String id) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            int rowsDeleted = db.delete(TABLE_PRODUCTS, COLUMN_ID + "=?", new String[]{id});
            if (rowsDeleted > 0) {
                Log.i("ProductDatabaseHelper", "Producto eliminado exitosamente.");
            } else {
                Log.e("ProductDatabaseHelper", "No se eliminó ningún producto. ID no válido: " + id);
            }
        } catch (Exception e) {
            Log.e("ProductDatabaseHelper", "Error al eliminar el producto", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public boolean productExists(String id) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        boolean exists = false;

        try {
            db = this.getReadableDatabase();
            // Consulta para verificar si el producto existe
            cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_ID + "=?", new String[]{id});

            // Si el cursor tiene datos, obtener el conteo
            if (cursor != null && cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                exists = count > 0;
            }
        } catch (Exception e) {
            Log.e("ProductDatabaseHelper", "Error verificando existencia de producto", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

             return exists;
    }
}

