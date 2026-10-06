package com.example.fittracker2;

public class Entrenamiento {

    private String ejercicio;
    private String duracion;
    private String calorias;
    private String fecha;

    public Entrenamiento(String ejercicio, String duracion,
                         String calorias, String fecha) {
        this.ejercicio = ejercicio;
        this.duracion = duracion;
        this.calorias = calorias;
        this.fecha = fecha;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getCalorias() {
        return calorias;
    }

    public String getFecha() {
        return fecha;
    }
}