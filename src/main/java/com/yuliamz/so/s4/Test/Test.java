package com.yuliamz.so.s4.Test;

import com.yuliamz.so.s4.Modelo.AdministradorProcesos;
import com.yuliamz.so.s4.Modelo.Particion;
import com.yuliamz.so.s4.Modelo.Proceso;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {
    
    public static void main(String[] args) {
        ArrayList<Particion> listaParticiones = new ArrayList<>();
        final Particion particion1 = new Particion("pp1", new BigInteger("10"));
        listaParticiones.add(particion1);
        
        final Particion particion2 = new Particion("pp2", new BigInteger("10"));
        listaParticiones.add(particion2);
        
        final ArrayList<Proceso> procesos1 = particion1.getProcesos();
        final ArrayList<Proceso> procesos2 = particion2.getProcesos();
        
        procesos2.add(new Proceso("p1", new BigInteger("13"), new BigInteger("10"), particion2));
        procesos2.add(new Proceso("p2", new BigInteger("5"), new BigInteger("11"), particion2));
        procesos1.add(new Proceso("p3", new BigInteger("8"), new BigInteger("5"), particion1));
        procesos1.add(new Proceso("p4", new BigInteger("6"), new BigInteger("10"), particion1));
        
//        list.add(new Proceso("P1", 10, true));
//        list.add(new Proceso("P2", 5, false));
//        list.add(new Proceso("P3", 20, false));
        try {
            AdministradorProcesos administradorProcesos = new AdministradorProcesos(listaParticiones);
            administradorProcesos.iniciarSecuencia();
            final ArrayList<Particion> particiones = administradorProcesos.getParticiones();
            for (Particion particion : particiones) {
                System.out.println();
                System.out.println(particion.getNombre());
                System.out.println("--- procesados");
                mostrarLista(particion.getProcesados());
                System.out.println("--- no procesados");
                mostrarLista(particion.getNoProcesados());
                System.out.println("--- listos");
                mostrarLista(particion.getListos());
                System.out.println("--- ejecutados");
                mostrarLista(particion.getEjecutados());
            }
            System.out.println();
            System.out.println("Pila");
            mostrarLista(administradorProcesos.getPilaProcesos());
//            System.out.println("========Listos==========");
//            mostrarLista(administradorProcesos.getListos());
//            System.out.println("========Despachados======");
//            mostrarLista(administradorProcesos.getDespachados());
//            System.out.println("========Ejecucion======");
//            mostrarLista(administradorProcesos.getEjecucion());
//            System.out.println("========Expiracion de Tiempo======");
//            mostrarLista(administradorProcesos.getExpiracionTiempo());
//            System.out.println("========Bloqueando======");
//            mostrarLista(administradorProcesos.getESejecucionBloqueado());
//            System.out.println("========Bloqueados======");
//            mostrarLista(administradorProcesos.getBloqueados());
//            System.out.println("========Despertados======");
//            mostrarLista(administradorProcesos.getESbloqueadoListo());
//            System.out.println("========Finalizados======");
//            mostrarLista(administradorProcesos.getFinalizados());
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void mostrarLista(ArrayList<Proceso> list) {
        list.forEach(System.out::println);
    }
    
}
