package com.example.hospitalsanpedro;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.HashMap;
import java.util.Map;
import okhttp3.ResponseBody;

public class AddVitalsActivity extends AppCompatActivity {
    TextInputEditText etPeso, etPresion, etTemp, etFc, etFr, etSat;
    Button btnAdd;
    ApiService api;
    SharedPrefManager sp;
    int citaId;

    @Override protected void onCreate(Bundle s){
        super.onCreate(s); setContentView(R.layout.activity_add_vitals);
        etPeso = findViewById(R.id.etPeso);
        etPresion = findViewById(R.id.etPresion);
        etTemp = findViewById(R.id.etTemp);
        etFc = findViewById(R.id.etFc);
        etFr = findViewById(R.id.etFr);
        etSat = findViewById(R.id.etSat);
        btnAdd = findViewById(R.id.btnAddSignos);

        api = ApiClient.getApiService(this);
        sp = new SharedPrefManager(this);

        citaId = getIntent().getIntExtra("cita_id", -1);
        String nombre = getIntent().getStringExtra("nombre_paciente");
        findViewById(R.id.tvPaciente).setVisibility(nombre != null ? findViewById(R.id.tvPaciente).VISIBLE : findViewById(R.id.tvPaciente).INVISIBLE);
        ((android.widget.TextView)findViewById(R.id.tvPaciente)).setText("Paciente: " + (nombre != null ? nombre : ""));

        btnAdd.setOnClickListener(v -> {
            String peso = etPeso.getText().toString().trim();
            String presion = etPresion.getText().toString().trim();
            String temp = etTemp.getText().toString().trim();
            String fc = etFc.getText().toString().trim();
            String fr = etFr.getText().toString().trim();
            String sat = etSat.getText().toString().trim();

            if(TextUtils.isEmpty(peso) || TextUtils.isEmpty(presion) || TextUtils.isEmpty(temp) ||
                    TextUtils.isEmpty(fc) || TextUtils.isEmpty(fr) || TextUtils.isEmpty(sat)){
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show(); return;
            }

            Map<String,Object> body = new HashMap<>();
            body.put("cita", citaId);
            body.put("peso", peso);
            body.put("presion_arterial", presion);
            body.put("temperatura", temp);
            body.put("frecuencia_cardiaca", fc);
            body.put("frecuencia_respiratoria", fr);
            body.put("saturacion_oxigeno", sat);

            api.postSignos("Token " + sp.getToken(), body).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(AddVitalsActivity.this, "Signos guardados", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddVitalsActivity.this, "Error guardando signos", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(AddVitalsActivity.this, "Error conexión", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }
}
