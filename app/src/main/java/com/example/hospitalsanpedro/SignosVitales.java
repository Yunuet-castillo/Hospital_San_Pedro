package com.example.hospitalsanpedro;

public class SignosVitales extends android.app.Activity {
    private int id;
    private int cita;
    private String peso;
    private String presion_arterial;
    private String temperatura;
    private int frecuencia_cardiaca;
    private int frecuencia_respiratoria;
    private int saturacion_oxigeno;

    public SignosVitales() {}

    public SignosVitales(int cita, String peso, String presion_arterial, String temperatura,
                         int frecuencia_cardiaca, int frecuencia_respiratoria, int saturacion_oxigeno) {
        this.cita = cita;
        this.peso = peso;
        this.presion_arterial = presion_arterial;
        this.temperatura = temperatura;
        this.frecuencia_cardiaca = frecuencia_cardiaca;
        this.frecuencia_respiratoria = frecuencia_respiratoria;
        this.saturacion_oxigeno = saturacion_oxigeno;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCita() { return cita; }
    public void setCita(int cita) { this.cita = cita; }

    public String getPeso() { return peso; }
    public void setPeso(String peso) { this.peso = peso; }

    public String getPresion_arterial() { return presion_arterial; }
    public void setPresion_arterial(String presion_arterial) { this.presion_arterial = presion_arterial; }

    public String getTemperatura() { return temperatura; }
    public void setTemperatura(String temperatura) { this.temperatura = temperatura; }

    public int getFrecuencia_cardiaca() { return frecuencia_cardiaca; }
    public void setFrecuencia_cardiaca(int frecuencia_cardiaca) { this.frecuencia_cardiaca = frecuencia_cardiaca; }

    public int getFrecuencia_respiratoria() { return frecuencia_respiratoria; }
    public void setFrecuencia_respiratoria(int frecuencia_respiratoria) { this.frecuencia_respiratoria = frecuencia_respiratoria; }

    public int getSaturacion_oxigeno() { return saturacion_oxigeno; }
    public void setSaturacion_oxigeno(int saturacion_oxigeno) { this.saturacion_oxigeno = saturacion_oxigeno; }
}
