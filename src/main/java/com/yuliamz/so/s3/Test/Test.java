package com.yuliamz.so.s3.Test;

import com.yuliamz.so.s3.Modelo.AdministradorProcesos;
import com.yuliamz.so.s3.Modelo.Proceso;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {
    
    public static void main(String[] args) {
        ArrayList<Proceso> list = new ArrayList<>();
//        list.add(new Proceso("P1", 10, true));
//        list.add(new Proceso("P2", 5, false));
//        list.add(new Proceso("P3", 20, false));
        try {
            AdministradorProcesos administradorProcesos = new AdministradorProcesos(list);
            administradorProcesos.iniciarSecuencia();
            System.out.println("========Listos==========");
            mostrarLista(administradorProcesos.getListos());
            System.out.println("========Despachados======");
            mostrarLista(administradorProcesos.getDespachados());
            System.out.println("========Ejecucion======");
            mostrarLista(administradorProcesos.getEjecucion());
            System.out.println("========Expiracion de Tiempo======");
            mostrarLista(administradorProcesos.getExpiracionTiempo());
            System.out.println("========Bloqueando======");
            mostrarLista(administradorProcesos.getESejecucionBloqueado());
            System.out.println("========Bloqueados======");
            mostrarLista(administradorProcesos.getBloqueados());
            System.out.println("========Despertados======");
            mostrarLista(administradorProcesos.getESbloqueadoListo());
            System.out.println("========Finalizados======");
            mostrarLista(administradorProcesos.getFinalizados());
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void mostrarLista(ArrayList<Proceso> list) {
        list.forEach(System.out::println);
    }
    
}
