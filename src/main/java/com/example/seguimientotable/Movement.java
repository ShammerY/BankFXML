package com.example.seguimientotable;

import java.util.Date;

public class Movement {
    private double monto;
    private String descripcion;
    private String fecha;
    private MovementType tipo;
    public Movement(double monto,String description,String date, MovementType tipo){
        this.monto = monto;
        this.descripcion = description;
        this.fecha = date;
        this.tipo = tipo;
    }
    public double getMonto() {
        return monto;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getFecha() {
        return fecha;
    }
    public MovementType getTipo(){
        return tipo;
    }
}
