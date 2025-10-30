package com.example.hospitalsanpedro;
import com.example.hospitalsanpedro.SignosVitales;

public class Cita {
    private int id;
    private String fecha;
    private String hora;
    private String estado;

    // Campo anidado para Signos Vitales
    private SignosVitales signosvitales;

    // Campo anidado para Paciente
    private Paciente paciente;

    // ¡CRUCIAL! Constructor vacío para que Retrofit/GSON funcione
    public Cita() {
    }

    // Constructor con parámetros (Opcional, pero útil)
    public Cita(int id, String fecha, String hora, String estado, SignosVitales signosvitales, Paciente paciente) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.signosvitales = signosvitales;
        this.paciente = paciente;
    }

    // Getters
    public int getId(){ return id; }
    public String getFecha(){ return fecha; }
    public String getHora(){ return hora; }
    public String getEstado(){ return estado; }
    public SignosVitales getSignosVitales() { return signosvitales; }
    public Paciente getPaciente(){ return paciente; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setHora(String hora) { this.hora = hora; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setSignosVitales(SignosVitales signosvitales) { this.signosvitales = signosvitales; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
}