package com.yuliamz.so.s3.controlador;

import com.yuliamz.so.s3.Modelo.AdministradorProcesos;
import com.yuliamz.so.s3.Modelo.Proceso;
import com.yuliamz.so.s3.Modelo.TablaVistaProcesos;
import com.yuliamz.so.s3.Vista.MaskField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Yuliamz
 */
public class Controller implements Initializable {

    ObservableList<Proceso> listaProcesos;
    ObservableList<TablaVistaProcesos> listaReporte;
    AdministradorProcesos ap;

    @FXML private Pane panelProcesos, panelReportes;
    @FXML private TextField txtNombreProceso;
    @FXML private MaskField numTiempo;
    @FXML private CheckBox checkBloqueo,checkSuspendidoBloqueado,checkSuspendidoListo;
    @FXML private Label infoBloqueo, errorNombre;
    @FXML private TableView<Proceso> tablaProcesos;
    @FXML private TableView<TablaVistaProcesos> tablaReportes;
    @FXML private TableColumn<Proceso, String> columnaBloqueo,columnaSuspendidoBloqueado,columnaSuspendidoListo;

    @FXML
    void limpiar(ActionEvent event) {
        checkBloqueo.setSelected(false);
        checkSuspendidoBloqueado.setSelected(false);
        checkSuspendidoListo.setSelected(false);
        numTiempo.setPlainText("");
        txtNombreProceso.setText("");
        infoBloqueo.setVisible(false);
        errorNombre.setVisible(false);
    }

    boolean validarCampos(){
        if(txtNombreProceso.getText().replace(" ", "").isEmpty()){
            errorNombre.setText("El nombre del proceso no puede estar vacío");
            errorNombre.setVisible(true);
            return false;
        }
        
        if(listaProcesos.stream().anyMatch(e -> e.getNombre().equals(txtNombreProceso.getText()))){
            errorNombre.setText("El nombre ya registrado, por favor elija uno nuevo");
            errorNombre.setVisible(true);
            return false;
        }
        if(numTiempo.getText().isEmpty()){
            errorNombre.setText("El tiempo del proceso no puede estar vacío");
            errorNombre.setVisible(true);
            return false;
        }
        if(numTiempo.getText().replaceAll("0+", "0").equals("0")){
            errorNombre.setText("El tiempo del proceso debe ser superior a 0");
            errorNombre.setVisible(true);
            return false;
        }
        return true;
    }
    
    @FXML
    void crearProceso(ActionEvent event) {
        if (validarCampos()) {
            String nombre= txtNombreProceso.getText().trim();
            int tiempo = Integer.parseInt(numTiempo.getText());
            listaProcesos.add(new Proceso(nombre, tiempo, checkBloqueo.isSelected(),checkSuspendidoBloqueado.isSelected(),checkSuspendidoListo.isSelected()));
            limpiar(event);
        }
    }
    
    @FXML
    void reportes(ActionEvent event) {
        if (!panelReportes.isVisible()) mostrarReportes();
    }
    @FXML
    void procesos(ActionEvent event) {
        if (!panelProcesos.isVisible()) mostrarProcesos();
    }

    @FXML
    void iniciarProcesos(ActionEvent event) {
        if (listaProcesos.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No hay procesos");
            alert.setHeaderText("No existen procesos para la simulación");
            alert.setContentText("Asegurese de ingresar al menos un(1) proceso antes de iniciar la simulación");
            alert.initOwner(panelProcesos.getScene().getWindow());
            alert.showAndWait();
        }else{
            ap = new AdministradorProcesos(new ArrayList<>(listaProcesos));
        try {
            ap.iniciarSecuencia();
            
            for (int i = 0; i < ap.getListos().size(); i++) {
                TablaVistaProcesos procesosReporte = new TablaVistaProcesos();
                try {procesosReporte.setBloqueado(ap.getBloqueados().get(i).getNombre());} catch (Exception e) {}
                try {procesosReporte.setDespachar(ap.getDespachados().get(i).getNombre());} catch (Exception e) {}
                try {procesosReporte.setESbloqueadoListo(ap.getESbloqueadoListo().get(i).getNombre());} catch (Exception e) {}
                try {procesosReporte.setESejecucionBloqueado(ap.getESejecucionBloqueado().get(i).getNombre());} catch (Exception e) {}
                try {procesosReporte.setESsuspendidoBloqueadoSuspendidoListo(ap.getESsuspendidoBloqueadoSuspendidoListo().get(i).getNombre());} catch (Exception e) {}
                try {procesosReporte.setEjecucion(ap.getEjecucion().get(i).getNombre());} catch (Exception e) {}
                try {procesosReporte.setExpTiempo(ap.getExpiracionTiempo().get(i).getNombre());} catch (Exception e) {}
                try {procesosReporte.setFin(ap.getFinalizados().get(i).getNombre());} catch (Exception e) {}
                try {procesosReporte.setListo(ap.getListos().get(i).getNombre());} catch (Exception e) {}
                try {procesosReporte.setReanudarSuspendidoBloqueadoBloqueado(ap.getReanudarSuspendidoBloqueadoBloqueado().get(i).getNombre());} catch (Exception e) {}
                try {procesosReporte.setReanudarSuspendidoListoListo(ap.getReanudarSuspendidoListoListo().get(i).getNombre());} catch (Exception e) {}
                try {procesosReporte.setSuspenderBloqueadoSuspendidoBloqueado(ap.getSuspenderBloqueadoSuspendidoBloqueado().get(i).getNombre());} catch (Exception e) {}
                try {procesosReporte.setSuspenderEjecucionSuspendidoListo(ap.getSuspenderEjecucionSuspendidoListo().get(i).getNombre());} catch (Exception e) {}
                try {procesosReporte.setSuspendidoBloqueado(ap.getSuspendidoBloqueado().get(i).getNombre());} catch (Exception e) {}
                try {procesosReporte.setSuspendidoListo(ap.getSuspendidoListo().get(i).getNombre());} catch (Exception e) {}
                listaReporte.add(procesosReporte);
            }
            mostrarReportes();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error inesperado");
            alert.setHeaderText("Ocurrió un error en la ejecución");
            alert.setContentText("Asegurese que la información insertada sea correcta");
            alert.initOwner(panelProcesos.getScene().getWindow());
            alert.showAndWait();
        }
        }
    }

    void mostrarReportes() {
        panelProcesos.setVisible(false);
        panelReportes.setVisible(true);
    }

    void mostrarProcesos() {
        panelReportes.setVisible(false);
        panelProcesos.setVisible(true);
    }

    ObservableList<String> getObservableListFrom(ArrayList<Proceso> list) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        list.forEach(e -> observableList.add(e.getNombre() + " - " + e.getTiempo() + "(u)"));
        return observableList;
    }

    @FXML
    void acercaDe(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Acerca de...");
        alert.setHeaderText("Software 3 de Sistemas Operativos");
        alert.setContentText("\nAutores:\n    *Julian David Grijalba Bernal\n    *William Desiderio Gil Farfan");
        alert.initOwner(panelProcesos.getScene().getWindow());
        alert.showAndWait();
    }

    @FXML
    void salir(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    private void eliminarProceso(ActionEvent event) {
        if (tablaProcesos.getSelectionModel().getSelectedIndex() >= 0) {
            tablaProcesos.getItems().remove(tablaProcesos.getSelectionModel().getFocusedIndex());
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No se puede eliminar");
            alert.setHeaderText("Debe seleccionar el proceso que desea eliminar");
            alert.initOwner(panelProcesos.getScene().getWindow());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listaProcesos = FXCollections.observableArrayList();
        listaReporte = FXCollections.observableArrayList();
        
        columnaBloqueo.setCellValueFactory(e -> {return new ReadOnlyStringWrapper(e.getValue().isBloqueado() ? "SI" : "NO");});
        columnaSuspendidoBloqueado.setCellValueFactory(e -> {return new ReadOnlyStringWrapper(e.getValue().isSuspendidoBloqueado() ? "SI" : "NO");});
        columnaSuspendidoListo.setCellValueFactory(e -> {return new ReadOnlyStringWrapper(e.getValue().isSuspendidoListo() ? "SI" : "NO");});
        
        tablaProcesos.setItems(listaProcesos);
        tablaReportes.setItems(listaReporte);
        
        txtNombreProceso.setTooltip(new Tooltip("El nombre del proceso debe iniciar con una \"P\" seguido de un número identificador"));
        numTiempo.setTooltip(new Tooltip("El tiempo del proceso debe ser superior a 0 y no puede estar vacío"));
    }

}
//<?import com.yuliamz.so.s3.Vista.*?>
