package com.example.hospitalsanpedro;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.HashMap;
import java.util.Map;
import okhttp3.ResponseBody; // Necesitas importar esta clase
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etUsername, etNombre, etApellidoP, etApellidoM, etEmail, etPassword, etPassword2;
    Button btnRegister;
    ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializar vistas
        etUsername = findViewById(R.id.etUsername);
        etNombre = findViewById(R.id.etNombre);
        etApellidoP = findViewById(R.id.etApellidoP);
        etApellidoM = findViewById(R.id.etApellidoM);
        etEmail = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        etPassword2 = findViewById(R.id.etPassword2);
        btnRegister = findViewById(R.id.btnRegister);

        api = ApiClient.getApiService(this);

        // Click en registrar
        btnRegister.setOnClickListener(v -> {
            // Extracción y validación de datos
            String username = etUsername.getText().toString().trim();
            String nombre = etNombre.getText().toString().trim();
            String apellidoP = etApellidoP.getText().toString().trim();
            String apellidoM = etApellidoM.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString();
            String password2 = etPassword2.getText().toString();

            // 1. Validar campos
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellidoP) ||
                    TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(password2)) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            // 2. Preparar body para API
            Map<String, Object> body = new HashMap<>();
            body.put("username", username);
            body.put("nombre", nombre);
            body.put("apellido_paterno", apellidoP);
            body.put("apellido_materno", apellidoM);
            body.put("email", email);
            body.put("password", password);
            body.put("role", "enfermera");

            // 3. Llamada API
            api.register(body).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        // Código 200/201: Registro exitoso
                        Toast.makeText(RegisterActivity.this, "¡Registro exitoso! Por favor, inicia sesión.", Toast.LENGTH_LONG).show();
                        // Ir a LoginActivity
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error de servidor (ej: 400 Bad Request)
                        try {
                            String errorBody = response.errorBody() != null ? response.errorBody().string() : "Error desconocido";
                            // Muestra el cuerpo del error que envía tu backend
                            Toast.makeText(RegisterActivity.this, "Error en registro: " + errorBody, Toast.LENGTH_LONG).show();
                            System.out.println("DEBUG: RESPUESTA DE ERROR DEL SERVIDOR - Código " + response.code());
                        } catch (Exception e) {
                            Toast.makeText(RegisterActivity.this, "Error en registro: Código " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Error de red/conexión (no se pudo llegar al servidor)
                    System.out.println("DEBUG: FALLO LA CONEXION - " + t.getMessage());
                    Toast.makeText(RegisterActivity.this, "¡ERROR DE RED CONFIRMADO!", Toast.LENGTH_LONG).show();
                    t.printStackTrace(); // Imprime la traza completa en el Logcat
                }
            });
        }); // <-- Cierre del setOnClickListener
    } // <-- Cierre del método onCreate
} // <-- Cierre de la clase RegisterActivity-