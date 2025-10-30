package com.example.hospitalsanpedro;

public class Paciente {
    private int id;
    private String nombre;
    private String apellido; // Usamos un solo campo de apellido
    private String telefono; // <--- Faltaba la declaración del campo
    private String direccion;

    // CONSTRUCTOR VACÍO (CRUCIAL para Retrofit/GSON)
    public Paciente() {
    }

    // Constructor con todos los campos (Asegúrate que los nombres de las variables internas coincidan)
    public Paciente(int id, String nombre, String apellido, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido; // Usando el campo 'apellido' único
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getTelefono() { return telefono; }
    public String getDireccion() { return direccion; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; } // Setter corregido
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}