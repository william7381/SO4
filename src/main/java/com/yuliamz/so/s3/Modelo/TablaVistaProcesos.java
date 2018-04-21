package com.yuliamz.so.s3.Modelo;

/**
 *
 * @author Yuliamz
 */
@lombok.AllArgsConstructor
@lombok.Data
public class TablaVistaProcesos {
    public String listo="";
    public String despachar="";
    public String expTiempo="";
    public String ejecucion="";
    public String ESejecucionBloqueado="";
    public String bloqueado="";
    public String ESbloqueadoListo="";
    public String suspenderBloqueadoSuspendidoBloqueado="";
    public String suspendidoBloqueado="";
    public String reanudarSuspendidoBloqueadoBloqueado="";
    public String ESsuspendidoBloqueadoSuspendidoListo="";
    public String suspendidoListo="";
    public String suspenderEjecucionSuspendidoListo="";
    public String reanudarSuspendidoListoListo;
    public String fin="";

    public TablaVistaProcesos() {
    }
   
    
}
