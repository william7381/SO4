package com.yuliamz.so.s4.Modelo;

import java.math.BigInteger;
import java.util.ArrayList;

@lombok.Data
@lombok.AllArgsConstructor
public class Particion implements Cloneable {
    
    private String nombre;
    private BigInteger tamanio;
    private ArrayList<Proceso> procesos;
    private int index;
    private ArrayList<Proceso> listos;
    private ArrayList<Proceso> ejecutados;
    private ArrayList<Proceso> procesados;
    private ArrayList<Proceso> noProcesados;

    public Particion(String nombre, BigInteger tamanio) {
        this.nombre = nombre;
        this.tamanio = tamanio;
        this.index = 0;
        this.procesos = new ArrayList<>();
        this.listos = new ArrayList<>();
        this.ejecutados = new ArrayList<>();
        this.procesados = new ArrayList<>();
        this.noProcesados = new ArrayList<>();
    }
    
    public Particion clonar() throws CloneNotSupportedException {
        final Particion particion = (Particion) clone();
        particion.setProcesos(new ArrayList<>());
        return particion;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }

    public void clear() {
        this.index = 0;
        this.listos.clear();
        this.ejecutados.clear();
        this.procesados.clear();
        this.noProcesados.clear();
    }
    
}
