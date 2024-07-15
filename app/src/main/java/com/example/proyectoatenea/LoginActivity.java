package com.example.proyectoatenea;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity {
    // Declación de variables
    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button btnLogin;
    private Button btnRegister;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        TextInputLayout textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        TextInputLayout textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);

        editTextEmail = textInputLayoutEmail.getEditText();
        editTextPassword = textInputLayoutPassword.getEditText();
        btnLogin=findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);

        // InicializaFirebase Authentication
        mAuth = FirebaseAuth.getInstance();


        if (editTextEmail == null || editTextPassword == null) {
            Log.e("LoginActivity", "Una o más vistas son nulas");
            return;
        }

        if (btnLogin == null) {
            Log.e("LoginActivity", "ButtonLogin es nulo");
            return;
        }
        // Listener para redirigir a login
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
                // Finaliza esta actividad para que el usuario no pueda volver atrás
            }
        });
        // Configuración del botón de inicio de sesión para llamar al método loginUser()
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    // Método para iniciar sesión
    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Validar entrada para campos no vacios
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        // Iniciar sesión con Firebase Authentication y lamado de escuchador
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Inicio de sesión exitoso mostrar el mensaje
                            Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            // Cierra esta actividad para que el usuario no pueda volver atrás
                            finish();
                        } else {
                            // Fallo en inicio de sesión y muestra mensaje de error
                            Toast.makeText(LoginActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}