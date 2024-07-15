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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button btnRegister;
    private Button btnLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);



        // Inicializa Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Vincula los componentes de la interfaz de usuario con las variables
        editTextName = findViewById(R.id.textFieldName);
        editTextEmail = findViewById(R.id.textFieldMail);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        editTextPassword = findViewById(R.id.textFieldPass);

        // Inicializa Firebase Firestore (Iniicializo la base de datos. )
        db = FirebaseFirestore.getInstance();

        //Evento de itnenación para abrir vista de Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegisterActivity.this,LoginActivity.class);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser(){
        String name = editTextName.getText().toString().trim();
        String email =editTextEmail.getText().toString().trim();
        String password= editTextPassword.getText().toString().trim();

        // Valida que nada venga vacio
        if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica que la contraseña tenga al menos 6 caracteres
        if (password.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crea el usuario en Firebase Authentication

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Usuario registrado conéxit en Firebase Autenticatión
                    Log.d("Firebase Authentication", "Usuario registrado con éxito");
                    FirebaseUser user= mAuth.getCurrentUser();
                    if(user != null){
                        //REgistro de información del usuario en la Firestore
                        // El map funciona como una llave valor. Alamacen identificador y objeto
                        Map<String, Object> userData= new HashMap<>();
                        userData.put("name", name);
                        userData.put("email", email);
                        userData.put("password",password);

                        db.collection("usuarios")
                                .document(user.getUid())
                                .set(userData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        // Usuario registrado exitosamente en Firestore
                                        Log.d("Firestore", "Usuario registrado exitosamente");
                                        Toast.makeText(RegisterActivity.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        // Limpiar los campos después del registro exitoso
                        editTextName.setText("");
                        editTextEmail.setText("");
                        editTextPassword.setText("");

                        // Redirige al usuario a la pantalla de inicio de sesión
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish(); // Cierra esta actividad para que el usuario no pueda volver atrás
                    }
                }
                else {
                    // Si falla la autenticación, muestra un mensaje de error
                    Log.e("Firebase Authentication", "Error al registrar usuario en Firebase Authentication", task.getException());
                    Toast.makeText(RegisterActivity.this, "Error al registrar usuario en Firebase Authentication", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    }



