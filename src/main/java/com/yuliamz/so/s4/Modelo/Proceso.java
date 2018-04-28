package com.yuliamz.so.s4.Modelo;

import java.math.BigInteger;

@lombok.Data
@lombok.AllArgsConstructor
public class Proceso implements Cloneable {
    
    private String nombre;
    private BigInteger tiempo;
    private BigInteger tamanio;
    private Particion particion;
    
    public Proceso clonar() throws CloneNotSupportedException {
        return (Proceso) clone();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return this.nombre + " " + this.tiempo + " ";
    }
    
}
