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
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author equipo
 */
public class TabParticion extends Tab {
    BorderPane borderPane = new BorderPane();

    public TabParticion(String nombre) {
        this.setText(nombre);
    }

    public TabParticion(Particion particion, TabPane tabPane) {
        this.setText(particion.getNombre());
        Label label = new Label("Tamaño de la particion: " + particion.getTamanio().toString());
        label.setTextFill(Color.ORANGERED);
        HBox hBox = new HBox(label);
        label.setPadding(new Insets(15, 12, 15, 12));
        hBox.setAlignment(Pos.CENTER);
        borderPane.setTop(hBox);
        crearDesde(particion.getProcesos(), particion.getProcesados(), particion.getNoProcesados());
    }
    
    public void crearDesde(ArrayList<Proceso> procesos, ArrayList<Proceso> procesados, ArrayList<Proceso> noProcesados) {
        ObservableList<TablaVistaParticiones> observableList = FXCollections.observableArrayList();
        
        TableView<TablaVistaParticiones> tableView = new TableView<>(observableList);
        
        TableColumn<TablaVistaParticiones, String> tableColumn1 = new TableColumn<>("Lista de Procesos");
        tableColumn1.setCellValueFactory(new PropertyValueFactory<>("proceso"));
        tableColumn1.setStyle("-fx-alignment: CENTER;");
        tableColumn1.setSortable(false);
        
        TableColumn<TablaVistaParticiones, String> tableColumn2 = new TableColumn<>("Tiempo de proceso");
        tableColumn2.setCellValueFactory(new PropertyValueFactory<>("tiempo"));
        tableColumn2.setStyle("-fx-alignment: CENTER;");
        tableColumn2.setSortable(false);
        
        TableColumn<TablaVistaParticiones, String> tableColumn3 = new TableColumn<>("Tamaño de proceso");
        tableColumn3.setCellValueFactory(new PropertyValueFactory<>("tamanio"));
        tableColumn3.setStyle("-fx-alignment: CENTER;");
        tableColumn3.setSortable(false);
        
        TableColumn<TablaVistaParticiones, String> tableColumn4 = new TableColumn<>("Procesados");
        tableColumn4.setCellValueFactory(new PropertyValueFactory<>("procesado"));
        tableColumn4.setStyle("-fx-alignment: CENTER;");
        tableColumn4.setSortable(false);
        
        TableColumn<TablaVistaParticiones, String> tableColumn5 = new TableColumn<>("No Procesados");
//        tableColumn3.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
        tableColumn5.setCellValueFactory(new PropertyValueFactory<>("noProcesado"));
        tableColumn5.setStyle("-fx-alignment: CENTER;");
        tableColumn5.setSortable(false);
        
        tableView.getColumns().addAll(tableColumn1, tableColumn2, tableColumn3, tableColumn4, tableColumn5);
        
        for (int i = 0; i < procesos.size(); i++) {
            TablaVistaParticiones procesosParticiones = new TablaVistaParticiones();
            try {procesosParticiones.setProceso(procesos.get(i).getNombre());} catch (Exception e) {}
            try {procesosParticiones.setTiempo(procesos.get(i).getTiempo().toString());} catch (Exception e) {}
            try {procesosParticiones.setTamanio(procesos.get(i).getTamanio().toString());} catch (Exception e) {}
            try {procesosParticiones.setProcesado(procesados.get(i).getNombre());} catch (Exception e) {}
            try {procesosParticiones.setNoProcesado(noProcesados.get(i).getNombre());} catch (Exception e) {}
            observableList.add(procesosParticiones);
        }
        
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        borderPane.setCenter(tableView);
        this.setContent(borderPane);
    }

}
