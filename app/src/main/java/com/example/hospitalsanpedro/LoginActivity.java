package com.example.hospitalsanpedro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword;
    private MaterialButton btnLogin;
    private TextView tvRegister, tvForgotPassword;
    private MaterialCheckBox cbRememberCredentials;

    private SharedPrefManager sp;
    private SharedPreferences loginPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = new SharedPrefManager(this);

        // 🔁 AUTOLOGIN
        if (sp.getToken() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        loginPrefs = getSharedPreferences("login_prefs", MODE_PRIVATE);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        cbRememberCredentials = findViewById(R.id.cbRememberCredentials);

        cargarCredenciales();

        btnLogin.setOnClickListener(v -> realizarLogin());

        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class))
        );

        tvForgotPassword.setOnClickListener(v ->
                Toast.makeText(this, "Recuperación próximamente", Toast.LENGTH_SHORT).show()
        );
    }

    // ===============================
    // LOGIN REAL
    // ===============================
    private void realizarLogin() {

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService api = ApiClient.getApiService();

        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);

        api.login(body).enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    String token = response.body().getToken();

                    sp.saveToken(token);

                    guardarCredenciales(username, password);

                    Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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