package com.example.proyectoatenea;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoatenea.adapter.ProductAdapter;
import com.example.proyectoatenea.model.Product;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Product> productList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Cargar datos de ejemplo
        productList = new ArrayList<>();
        productList.add(new Product("Acetaminofen", "$10", R.drawable.login));
        productList.add(new Product("Ibuprofeno", "$12", R.drawable.login));
        productList.add(new Product("Dolex", "$12", R.drawable.login));
        productList.add(new Product("Sulfato", "$12", R.drawable.login));
        productList.add(new Product("Crema", "$12", R.drawable.login));
        productList.add(new Product("Aspirina", "$12", R.drawable.login));
        productList.add(new Product("Doloran", "$12", R.drawable.login));
        productList.add(new Product("Ibuprofeno", "$12", R.drawable.login));
        // Agrega más productos según sea necesario

        adapter = new ProductAdapter(productList, this);
        recyclerView.setAdapter(adapter);
    }
}