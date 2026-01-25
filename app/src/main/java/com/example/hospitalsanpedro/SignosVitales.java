package com.example.hospitalsanpedro;

public class SignosVitales {
    private int id;
    private int paciente;
    private String presion;
    private double temperatura;
    private int frecuenciaCardiaca;

    // 🔹 Constructor vacío (obligatorio para Retrofit/GSON)
    public SignosVitales() {
    }

    // 🔹 Constructor con argumentos (opcional)
    public SignosVitales(int paciente, String presion, double temperatura, int frecuenciaCardiaca) {
        this.paciente = paciente;
        this.presion = presion;
        this.temperatura = temperatura;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    // Getters y setters
    public int getId() { return id; }
    public int getPaciente() { return paciente; }
    public String getPresion() { return presion; }
    public double getTemperatura() { return temperatura; }
    public int getFrecuenciaCardiaca() { return frecuenciaCardiaca; }

    public void setId(int id) { this.id = id; }
    public void setPaciente(int paciente) { this.paciente = paciente; }
    public void setPresion(String presion) { this.presion = presion; }
    public void setTemperatura(double temperatura) { this.temperatura = temperatura; }
    public void setFrecuenciaCardiaca(int frecuenciaCardiaca) { this.frecuenciaCardiaca = frecuenciaCardiaca; }
}
