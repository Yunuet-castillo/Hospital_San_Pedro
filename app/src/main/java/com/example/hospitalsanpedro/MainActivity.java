package com.example.hospitalsanpedro;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PatientAdapter.OnRegisterClickListener {

    RecyclerView rv;
    ApiService api;
    SharedPrefManager sp;
    PatientAdapter adapter;
    FloatingActionButton btnMenuOpciones;
    TextView tvContador;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rvPacientes);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // 🔥 YA SIN CONTEXT
        api = ApiClient.getApiService();

        sp = new SharedPrefManager(this);

        adapter = new PatientAdapter(this);
        rv.setAdapter(adapter);

        btnMenuOpciones = findViewById(R.id.btnMenuOpciones);
        tvContador = findViewById(R.id.tvContador);

        btnMenuOpciones.setOnClickListener(this::mostrarMenuOpciones);

        loadCitas();
    }

    private void loadCitas() {
        String token = sp.getToken();

        if (token == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        api.getCitas("Token " + token, null).enqueue(new Callback<List<Cita>>() {

            @Override
            public void onResponse(Call<List<Cita>> call, Response<List<Cita>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setItems(response.body());
                    tvContador.setText(response.body().size() + " pacientes para hoy");
                } else {
                    Toast.makeText(MainActivity.this, "Error cargando citas: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cita>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRegisterClick(Cita cita) {
        Intent i = new Intent(this, AddVitalsActivity.class);
        i.putExtra("cita_id", cita.getId());
        i.putExtra("nombre_paciente",
                cita.getPaciente().getNombre() + " " +
                        cita.getPaciente().getApellidoPaterno() + " " +
                        cita.getPaciente().getApellidoMaterno()
        );
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCitas();
    }

    private void mostrarMenuOpciones(View view) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.menu_opciones_flotante, null);
        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setElevation(16f);
        popupWindow.showAsDropDown(view, -200, 0);

        // ❌ ELIMINADO: cambiar IP

        // ✅ SOLO CERRAR SESIÓN
        popupView.findViewById(R.id.opcionCerrarSesion).setOnClickListener(v -> {
            cerrarSesion();
            popupWindow.dismiss();
        });
    }

    private void cerrarSesion() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Cerrar sesión")
                .setMessage("¿Estás seguro de que quieres cerrar sesión?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    sp.clear();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
}