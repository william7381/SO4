package com.yuliamz.so.s4.Modelo;

import java.math.BigInteger;
import java.util.ArrayList;

@lombok.Getter
public class AdministradorProcesos {

    public static final BigInteger QUANTUM = new BigInteger("5");

    private final ArrayList<Particion> particiones;
    private final ArrayList<Proceso> pilaProcesos;
    private final ArrayList<Proceso> totalProcesados;
    private final ArrayList<Proceso> totalNoProcesados;
    
    public AdministradorProcesos(ArrayList<Particion> listaParticiones) throws CloneNotSupportedException {
        this.particiones = new ArrayList<>();
        for (Particion particion : listaParticiones) {
            Particion particionActual = particion.clonar();
            this.particiones.add(particionActual);
            for (Proceso proceso : particion.getProcesos()) {
                particionActual.getProcesos().add(proceso.clonar());
            }
        }
        this.pilaProcesos = new ArrayList<>();
        this.totalProcesados = new ArrayList<>();
        this.totalNoProcesados = new ArrayList<>();
    }

    public void iniciarSecuencia() throws CloneNotSupportedException {
        ArrayList<Particion> auxParticiones = (ArrayList<Particion>) particiones.clone();
        int index = 0;
        while(!auxParticiones.isEmpty()) {
            Particion particionActual = auxParticiones.get(index);
            if (particionActual.getProcesos().size() == 0) {
                auxParticiones.remove(particionActual);
            }else {
                if (particionActual.getListos().size() == 0) {
                    particionActual.getListos().addAll(particionActual.getProcesos());
//                    System.out.println(particionActual.getNombre() + "   " +particionActual.getListos().size());
                }
                procesarParticion(particionActual);
                if (particionActual.getIndex() >= particionActual.getListos().size()) {
                    auxParticiones.remove(particionActual);
                }
            }
            index = (index + 1 >= auxParticiones.size()) ? 0 : index + 1;
        }
    }

    private void procesarParticion(Particion particionActual) throws CloneNotSupportedException {
        Proceso procesoActual = particionActual.getListos().get(particionActual.getIndex()).clonar();
        if (procesoActual.getTamanio().compareTo(particionActual.getTamanio()) > 0) {
            particionActual.getNoProcesados().add(procesoActual);
            particionActual.setIndex(particionActual.getIndex() + 1);
            this.totalNoProcesados.add(procesoActual);
            return;
        }
        pilaProcesos.add(procesoActual.clonar());
        if (procesoActual.getTiempo().compareTo(QUANTUM) > 0) {
            procesoActual.setTiempo(procesoActual.getTiempo().subtract(QUANTUM));
            particionActual.getEjecutados().add(procesoActual);
            particionActual.getListos().add(procesoActual);
        }else {
            particionActual.getProcesados().add(procesoActual);
            procesoActual.setTiempo(BigInteger.ZERO);
            particionActual.getEjecutados().add(procesoActual);
            this.totalProcesados.add(procesoActual);
        }
        particionActual.setIndex(particionActual.getIndex() + 1);
    }
}
