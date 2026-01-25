package com.example.hospitalsanpedro;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class ServerConfigActivity extends AppCompatActivity {

    private EditText etI;
    private MaterialButton btnGuardarYContinuar;
    private SharedPrefManager sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_ip);

        etI = findViewById(R.id.etIp);
        btnGuardarYContinuar = findViewById(R.id.btnGuardarYContinuar);
        sp = new SharedPrefManager(this);

        // Cargar IP guardada si existe
        String ipGuardada = sp.getServerIp();
        if (ipGuardada != null && !ipGuardada.isEmpty()) {
            etI.setText(ipGuardada);
        }

        // Guardar IP y cerrar
        btnGuardarYContinuar.setOnClickListener(v -> guardarIp());
    }

    private void guardarIp() {
        String ip = etI.getText().toString().trim();

        if (ip.isEmpty()) {
            Toast.makeText(this, "Ingresa una IP válida", Toast.LENGTH_SHORT).show();
            return;
        }

        sp.saveServerIp(ip);

        Toast.makeText(this, "IP guardada correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }
}
