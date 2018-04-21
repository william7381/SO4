package com.yuliamz.so.s3.Modelo;

import java.util.ArrayList;

@lombok.Getter
public class AdministradorProcesos {
    public static final int QUANTUM = 5;
    
    private final ArrayList<Proceso> listos;
    private final ArrayList<Proceso> despachados;
    private final ArrayList<Proceso> ejecucion;
    private final ArrayList<Proceso> expiracionTiempo;
    private final ArrayList<Proceso> ESejecucionBloqueado;
    private final ArrayList<Proceso> bloqueados;
    private final ArrayList<Proceso> ESbloqueadoListo;
    private final ArrayList<Proceso> suspenderBloqueadoSuspendidoBloqueado;
    private final ArrayList<Proceso> suspendidoBloqueado;
    private final ArrayList<Proceso> reanudarSuspendidoBloqueadoBloqueado;
    private final ArrayList<Proceso> ESsuspendidoBloqueadoSuspendidoListo;
    private final ArrayList<Proceso> suspendidoListo;
    private final ArrayList<Proceso> suspenderEjecucionSuspendidoListo;
    private final ArrayList<Proceso> reanudarSuspendidoListoListo;
    private final ArrayList<Proceso> finalizados;

    public AdministradorProcesos(ArrayList<Proceso> listaProcesos){
        listos=listaProcesos;
        despachados = new ArrayList<>();
        ejecucion = new ArrayList<>();
        expiracionTiempo = new ArrayList<>();
        ESejecucionBloqueado = new ArrayList<>();
        bloqueados = new ArrayList<>();
        ESbloqueadoListo = new ArrayList<>();
        suspenderBloqueadoSuspendidoBloqueado = new ArrayList<>();
        suspendidoBloqueado = new ArrayList<>();
        reanudarSuspendidoBloqueadoBloqueado = new ArrayList<>();
        ESsuspendidoBloqueadoSuspendidoListo = new ArrayList<>();
        suspendidoListo = new ArrayList<>();
        suspenderEjecucionSuspendidoListo = new ArrayList<>();
        reanudarSuspendidoListoListo = new ArrayList<>();
        finalizados = new ArrayList<>();
    }
    
    public void iniciarSecuencia() throws CloneNotSupportedException {
        for (int i = 0; i < listos.size(); i++) {
            Proceso procesoActual = listos.get(i).clonar();
            despachados.add(procesoActual.clonar());
            ejecucion.add(procesoActual.clonar());
            if (procesoActual.getTiempo() <= QUANTUM) {
                procesoActual.setTiempo(0);
                finalizados.add(procesoActual.clonar());
            }else {
                procesoActual.setTiempo(procesoActual.getTiempo() - QUANTUM);
                if (procesoActual.isBloqueado()) {
                    ESejecucionBloqueado.add(procesoActual.clonar());
                    bloqueados.add(procesoActual.clonar());
                    if (procesoActual.isSuspendidoBloqueado) {
                        suspenderBloqueadoSuspendidoBloqueado.add(procesoActual.clonar());
                        suspendidoBloqueado.add(procesoActual.clonar());
                        if (procesoActual.isSuspendidoListo) {
                            ESsuspendidoBloqueadoSuspendidoListo.add(procesoActual.clonar());
                            suspendidoListo.add(procesoActual.clonar());
                            reanudarSuspendidoListoListo.add(procesoActual.clonar());
                        }else{
                            reanudarSuspendidoBloqueadoBloqueado.add(procesoActual.clonar());
                            bloqueados.add(procesoActual.clonar());
                            ESbloqueadoListo.add(procesoActual.clonar());
                        }
                    }else{
                        ESbloqueadoListo.add(procesoActual.clonar());
                    }
                    
                }else if (!procesoActual.isSuspendidoListo) {
                   expiracionTiempo.add(procesoActual.clonar());
                }
                
                if (procesoActual.isSuspendidoListo && !procesoActual.isSuspendidoBloqueado) {
                    suspenderEjecucionSuspendidoListo.add(procesoActual.clonar());
                    suspendidoListo.add(procesoActual.clonar());
                    reanudarSuspendidoListoListo.add(procesoActual.clonar());
                }

                listos.add(procesoActual.clonar());
                }
            
            }
        }
    }
    

