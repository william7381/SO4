package com.yuliamz.so.s4.Modelo;

import java.math.BigInteger;
import java.util.ArrayList;

@lombok.Getter
public class AdministradorProcesos {

    public static final BigInteger QUANTUM = new BigInteger("5");

    private final ArrayList<Particion> particiones;
    private final ArrayList<Proceso> pilaProcesos;
    
    public AdministradorProcesos(ArrayList<Particion> listaParticiones) throws CloneNotSupportedException {
        particiones = new ArrayList<>();
        for (Particion particion : listaParticiones) {
            Particion particionActual = particion.clonar();
            particiones.add(particionActual);
            for (Proceso proceso : particion.getProcesos()) {
                particionActual.getProcesos().add(proceso.clonar());
            }
        }
        pilaProcesos = new ArrayList<>();
    }

    public void iniciarSecuencia() throws CloneNotSupportedException {
        ArrayList<Particion> auxParticiones = (ArrayList<Particion>) particiones.clone();
        int index = 0;
        while(!auxParticiones.isEmpty()) {
            Particion particionActual = auxParticiones.get(index);
            if (particionActual.getProcesos().size() == 0) {
                auxParticiones.remove(particionActual);
            }else {
                procesarParticion(particionActual);
                if (particionActual.getIndex() == particionActual.getProcesos().size()) {
                    auxParticiones.remove(particionActual);
                }
            }
            index = (index + 1 >= auxParticiones.size()) ? 0 : index + 1;
        }
    }

    private void procesarParticion(Particion particionActual) throws CloneNotSupportedException {
        Proceso procesoActual = particionActual.getProcesos().get(particionActual.getIndex());
        if (procesoActual.getTamanio().compareTo(particionActual.getTamanio()) == 1) {
            particionActual.getNoProcesados().add(procesoActual);
            particionActual.setIndex(particionActual.getIndex() + 1);
            return;
        }
        pilaProcesos.add(procesoActual.clonar());
        if (procesoActual.getTiempo().compareTo(QUANTUM) > 0) {
            procesoActual.setTiempo(procesoActual.getTiempo().subtract(QUANTUM));
        }else {
            particionActual.getProcesados().add(procesoActual);
            procesoActual.setTiempo(BigInteger.ZERO);
            particionActual.setIndex(particionActual.getIndex() + 1);
        }
    }
}