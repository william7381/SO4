package com.yuliamz.so.s4.Modelo;

/**
 *
 * @author Yuliamz
 */
@lombok.AllArgsConstructor
@lombok.Data
public class TablaVistaParticiones {
    public String proceso = "";
    public String tiempo = "";
    public String tamanio = "";
    public String procesado = "";
    public String noProcesado = "";

    public TablaVistaParticiones() {}
    
}
