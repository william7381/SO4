/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yuliamz.so.s4.Vista;

import com.yuliamz.so.s4.Modelo.Particion;
import com.yuliamz.so.s4.Modelo.Proceso;
import com.yuliamz.so.s4.Modelo.TablaVistaParticiones;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author equipo
 */
public class TabParticion extends Tab {

    public TabParticion(String nombre) {
        this.setText(nombre);
    }

    public TabParticion(Particion particion, TabPane tabPane) {
        this.setText(particion.getNombre());
        crearDesde(particion.getProcesos(), particion.getProcesados(), particion.getNoProcesados());
    }
    
    public void crearDesde(ArrayList<Proceso> procesos, ArrayList<Proceso> procesados, ArrayList<Proceso> noProcesados) {
        ObservableList<TablaVistaParticiones> observableList = FXCollections.observableArrayList();
        
        TableView<TablaVistaParticiones> tableView = new TableView<>(observableList);
        
        TableColumn<TablaVistaParticiones, String> tableColumn1 = new TableColumn<>("Lista de Procesos");
        tableColumn1.setCellValueFactory(new PropertyValueFactory<>("proceso"));
        tableColumn1.setSortable(false);
        
        TableColumn<TablaVistaParticiones, String> tableColumn2 = new TableColumn<>("Procesados");
        tableColumn2.setCellValueFactory(new PropertyValueFactory<>("procesado"));
        tableColumn2.setSortable(false);
        
        TableColumn<TablaVistaParticiones, String> tableColumn3 = new TableColumn<>("No Procesados");
//        tableColumn3.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
        tableColumn3.setCellValueFactory(new PropertyValueFactory<>("noProcesado"));
        tableColumn3.setSortable(false);
        
        tableView.getColumns().addAll(tableColumn1, tableColumn2, tableColumn3);
        
        for (int i = 0; i < procesos.size(); i++) {
            TablaVistaParticiones procesosParticiones = new TablaVistaParticiones();
            try {procesosParticiones.setProceso(procesos.get(i).getNombre());} catch (Exception e) {}
            try {procesosParticiones.setProcesado(procesados.get(i).getNombre());} catch (Exception e) {}
            try {procesosParticiones.setNoProcesado(noProcesados.get(i).getNombre());} catch (Exception e) {}
            observableList.add(procesosParticiones);
        }
        
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.setContent(tableView);
    }

}
