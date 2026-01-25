package com.example.hospitalsanpedro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword;
    private MaterialButton btnLogin;
    private TextView tvRegister, tvForgotPassword;
    private FloatingActionButton btnMenuOpciones;
    private MaterialCheckBox cbRememberCredentials;

    private SharedPrefManager sp;
    private SharedPreferences loginPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // SharedPrefManager (token, IP, etc.)
        sp = new SharedPrefManager(this);


        // 🔁 Autologin
        if (sp.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        // SharedPreferences para login
        loginPrefs = getSharedPreferences("login_prefs", MODE_PRIVATE);

        // Referencias XML
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        btnMenuOpciones = findViewById(R.id.btnMenuOpciones);
        cbRememberCredentials = findViewById(R.id.cbRememberCredentials);

        // 🔁 Cargar credenciales guardadas
        cargarCredenciales();

        // 🔧 Forzar engranaje arriba
        btnMenuOpciones.bringToFront();
        btnMenuOpciones.setClickable(true);

        // Engranaje → configuración IP
        btnMenuOpciones.setOnClickListener(v -> {
            Toast.makeText(this, "Configuración del servidor", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, ServerConfigActivity.class));
        });

        // Login
        btnLogin.setOnClickListener(v -> realizarLogin());

        // Registro (opcional)
        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            Toast.makeText(this, "Registro próximamente", Toast.LENGTH_SHORT).show();
        });

        // Olvidé contraseña (opcional)
        tvForgotPassword.setOnClickListener(v ->
                Toast.makeText(this, "Recuperación próximamente", Toast.LENGTH_SHORT).show()
        );
    }

    // ===============================
    // LOGIN
    // ===============================
    private void realizarLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ Verificar IP del servidor
        String baseUrl = sp.getBaseUrl();
        if (baseUrl == null || baseUrl.isEmpty()) {
            Toast.makeText(this,
                    "Configura la IP del servidor antes de iniciar sesión",
                    Toast.LENGTH_LONG).show();
            return;
        }

        // 🔐 LOGIN SIMULADO CORRECTO
        sp.saveToken("TOKEN_DE_PRUEBA_123");

        // 💾 Guardar o borrar credenciales
        guardarCredenciales(username, password);

        Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    // ===============================
    // RECORDAR CREDENCIALES
    // ===============================
    private void guardarCredenciales(String username, String password) {
        SharedPreferences.Editor editor = loginPrefs.edit();

        if (cbRememberCredentials.isChecked()) {
            editor.putString("username", username);
            editor.putString("password", password);
            editor.putBoolean("remember", true);
        } else {
            editor.clear();
        }

        editor.apply();
    }

    private void cargarCredenciales() {
        boolean remember = loginPrefs.getBoolean("remember", false);

        if (remember) {
            etUsername.setText(loginPrefs.getString("username", ""));
            etPassword.setText(loginPrefs.getString("password", ""));
            cbRememberCredentials.setChecked(true);
        }
    }
}
