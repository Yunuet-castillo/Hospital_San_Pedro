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

    public interface OnRegisterClickListener {
        void onRegisterClick(Cita cita);
    }

    public PatientAdapter(OnRegisterClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<Cita> list) {
        items.clear();
        items.addAll(list);
        notifyDataSetChanged();
    }

    // 🔥 Método para eliminar una cita
    public void removeItem(Cita cita) {
        int index = items.indexOf(cita);
        if (index != -1) {
            items.remove(index);
            notifyItemRemoved(index);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_patient, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Cita c = items.get(pos);
        Paciente p = c.getPaciente();

        String aPaterno = p.getApellidoPaterno() != null ? p.getApellidoPaterno() : "";
        String aMaterno = p.getApellidoMaterno() != null ? p.getApellidoMaterno() : "";

        String nombreCompleto = (p.getNombre() + " " + aPaterno + " " + aMaterno).trim();

        h.tvPacienteId.setText("ID: " + p.getId());
        h.tvNombre.setText(nombreCompleto);
        h.tvFecha.setText(c.getFecha());
        h.tvHora.setText(c.getHora());

        h.btnRegistrar.setOnClickListener(v -> listener.onRegisterClick(c));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvPacienteId, tvNombre, tvFecha, tvHora;
        Button btnRegistrar;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvPacienteId = itemView.findViewById(R.id.tvPacienteId);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvHora = itemView.findViewById(R.id.tvHora);
            btnRegistrar = itemView.findViewById(R.id.btnRegistrar);
        }
    }
}