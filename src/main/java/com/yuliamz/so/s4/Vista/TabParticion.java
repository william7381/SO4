/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yuliamz.so.s4.Vista;

import com.yuliamz.so.s4.Modelo.Particion;
import com.yuliamz.so.s4.Modelo.Proceso;
import com.yuliamz.so.s4.Modelo.TablaVistaParticiones;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author equipo
 */
public class TabParticion extends Tab {

    public TabParticion(Particion particion) {
        this.setText(particion.getNombre());
        
        ObservableList<TablaVistaParticiones> observableList = FXCollections.observableArrayList();
        
        TableView<TablaVistaParticiones> tableView = new TableView<>(observableList);
        
        TableColumn<TablaVistaParticiones, String> tableColumn1 = new TableColumn<>("Lista de Procesos");
        tableColumn1.setCellValueFactory(new PropertyValueFactory<>("proceso"));
        TableColumn<TablaVistaParticiones, String> tableColumn2 = new TableColumn<>("Procesados");
        tableColumn2.setCellValueFactory(new PropertyValueFactory<>("procesado"));
        TableColumn<TablaVistaParticiones, String> tableColumn3 = new TableColumn<>("No Procesados");
        tableColumn3.setCellValueFactory(new PropertyValueFactory<>("noProcesado"));
        
        tableView.getColumns().addAll(tableColumn1, tableColumn2, tableColumn3);
        
        for (int i = 0; i < particion.getProcesos().size(); i++) {
            TablaVistaParticiones procesosParticiones = new TablaVistaParticiones();
            try {procesosParticiones.setProceso(particion.getProcesos().get(i).getNombre());} catch (Exception e) {}
            try {procesosParticiones.setProcesado(particion.getProcesados().get(i).getNombre());} catch (Exception e) {}
            try {procesosParticiones.setNoProcesado(particion.getNoProcesados().get(i).getNombre());} catch (Exception e) {}
            observableList.add(procesosParticiones);
        }
        
        this.setContent(tableView);
    }

}
