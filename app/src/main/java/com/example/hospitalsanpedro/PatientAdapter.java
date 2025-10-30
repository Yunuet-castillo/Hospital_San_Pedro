package com.example.hospitalsanpedro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.VH> {
    private List<Cita> items = new ArrayList<>();
    private OnRegisterClickListener listener;

    public interface OnRegisterClickListener { void onRegisterClick(Cita cita); }

    public PatientAdapter(OnRegisterClickListener listener){ this.listener = listener; }

    public void setItems(List<Cita> list){ items.clear(); items.addAll(list); notifyDataSetChanged(); }

    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient, parent, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos){
        Cita c = items.get(pos);
        Paciente p = c.getPaciente();

        // 1. Construir el nombre completo (usando getApellido() en lugar de getApellido_paterno())
        // ¡ASUMO que tu clase Paciente.java tiene un método getApellido()!
        String nombreCompleto = p.getNombre() + " " + p.getApellido();

        // 2. Establecer el ID y el Nombre Completo
        // Asumo que tienes un nuevo TextView llamado tvPacienteId en tu XML
        // y que tu clase Paciente.java tiene un método getId().
        h.tvPacienteId.setText("ID: " + p.getId());
        h.tvNombre.setText(nombreCompleto);

        // 3. Establecer Fecha y Hora (mostrando ambos)
        // Asumo que tienes un nuevo TextView llamado tvFecha en tu XML
        h.tvFecha.setText("Fecha: " + c.getFecha());
        h.tvHora.setText("Hora: " + c.getHora()); // Aquí solo va la hora si tvHora solo la muestra

        // Si solo tienes un TextView para fecha y hora:
        // h.tvHora.setText("Fecha y Hora: " + c.getFecha() + " - " + c.getHora());

        // 4. Configurar el botón
        h.btnRegistrar.setOnClickListener(v -> listener.onRegisterClick(c));
    }

    @Override public int getItemCount(){ return items.size(); }

    // Clase ViewHolder corregida para incluir el nuevo TextView del ID y de la Fecha
    static class VH extends RecyclerView.ViewHolder{
        TextView tvPacienteId, tvNombre, tvFecha, tvHora; // Añade tvPacienteId y tvFecha
        Button btnRegistrar;
        public VH(@NonNull View itemView){
            super(itemView);
            // ¡Asegúrate de que estos IDs existen en tu XML!
            tvPacienteId = itemView.findViewById(R.id.tvPacienteId);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvHora = itemView.findViewById(R.id.tvHora);
            btnRegistrar = itemView.findViewById(R.id.btnRegistrar);
        }
    }
}