package com.example.hospitalsanpedro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PatientAdapter.OnRegisterClickListener {

    // Variables de la Actividad
    RecyclerView rv;
    ApiService api;
    SharedPrefManager sp;
    PatientAdapter adapter;

    @Override
    protected void onCreate(Bundle s){
        super.onCreate(s);
        setContentView(R.layout.activity_main);

        // Inicialización de Vistas y Componentes
        rv = findViewById(R.id.rvPacientes);
        rv.setLayoutManager(new LinearLayoutManager(this));

        api = ApiClient.getApiService();
        sp = new SharedPrefManager(this);

        adapter = new PatientAdapter(this);
        rv.setAdapter(adapter);

        loadCitas();
    }

    private void loadCitas(){
        String token = sp.getToken();

        // 1. Verificación del Token (Seguridad)
        if(token == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // 2. Obtener la fecha de hoy en formato 'yyyy-MM-dd'
        // Este es el formato estándar para enviar fechas a una API/Base de datos
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = dateFormat.format(new Date());

        // 3. Llamada a la API con el token y la fecha de hoy
        api.getCitas("Token " + token, todayDate).enqueue(new Callback<List<Cita>>() {
            @Override
            public void onResponse(Call<List<Cita>> call, Response<List<Cita>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    // La respuesta contiene SOLO las citas filtradas por el servidor
                    adapter.setItems(response.body());
                } else {
                    // Muestra el código de error si la respuesta no fue exitosa
                    Toast.makeText(MainActivity.this,
                            "Error cargando citas: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cita>> call, Throwable t) {
                // Error de conexión o fallo de red
                Toast.makeText(MainActivity.this,
                        "Error de conexión: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    // Implementación del listener (callback del adapter)
    @Override
    public void onRegisterClick(Cita cita) {
        Intent i = new Intent(this, AddVitalsActivity.class);
        i.putExtra("cita_id", cita.getId());
        // Se asume que estos métodos existen en tu clase Cita y Paciente
        i.putExtra("nombre_paciente", cita.getPaciente().getNombre() + " " + cita.getPaciente().getApellido());
        startActivity(i);
    }

    @Override
    protected void onResume(){
        super.onResume();
        // Recarga las citas cada vez que se regresa a esta actividad (por ejemplo, al volver de AddVitalsActivity)
        loadCitas();
    }
}