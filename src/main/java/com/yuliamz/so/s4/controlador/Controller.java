package com.yuliamz.so.s4.controlador;

import com.yuliamz.so.s4.Modelo.AdministradorProcesos;
import com.yuliamz.so.s4.Modelo.Particion;
import com.yuliamz.so.s4.Modelo.Proceso;
import com.yuliamz.so.s4.Vista.TabParticion;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

//<?import com.yuliamz.so.s4.Vista.*?>

/**
 * FXML Controller class
 *
 * @author Yuliamz
 */
public class Controller implements Initializable {

    private ObservableList<Particion> listaParticiones;
    private ObservableList<Proceso> listaProcesos;
    private ObservableList<Proceso> listaReporte;
    private AdministradorProcesos ap;

    @FXML private Pane panelProcesos, panelReportes;
    @FXML private TextField txtNombreParticion, txtTamanioParticion, txtNombreProceso, txtTiempoProceso, txtTamanioProceso;
    @FXML private ComboBox<Particion> comboBoxParticiones;
    @FXML private Label labelErrorParticion, labelErrorProceso;
    @FXML private Button btnReportes;
    @FXML private TableView<Proceso> tablaProcesos;
    @FXML private TableView<Particion> tablaParticiones;
    @FXML private TableView<Proceso> tablaReportes;
    @FXML private TabPane tabPaneParticiones;
    
    @FXML
    void limpiarParticion(ActionEvent event) {
        txtTamanioParticion.setText("");
        txtNombreParticion.setText("");
        labelErrorParticion.setVisible(false);
    }
    
    @FXML
    void limpiarProceso(ActionEvent event) {
        txtNombreProceso.setText("");
        txtTiempoProceso.setText("");
        txtTamanioProceso.setText("");
        comboBoxParticiones.getSelectionModel().selectFirst();
        labelErrorParticion.setVisible(false);
    }

    boolean validarCamposParticion(){
        if(txtNombreParticion.getText().replace(" ", "").isEmpty()){
            labelErrorParticion.setText("El nombre del proceso no puede estar vacío");
            labelErrorParticion.setVisible(true);
            return false;
        }
        if(listaParticiones.stream().anyMatch(e -> e.getNombre().equals(txtNombreParticion.getText()))){
            labelErrorParticion.setText("El nombre ya existe, por favor elija uno nuevo");
            labelErrorParticion.setVisible(true);
            return false;
        }
        if(txtTamanioParticion.getText().isEmpty()){
            labelErrorParticion.setText("El tiempo del proceso no puede estar vacío");
            labelErrorParticion.setVisible(true);
            return false;
        }
        if(txtTamanioParticion.getText().replaceAll("0+", "0").equals("0")){
            labelErrorParticion.setText("El tiempo del proceso debe ser superior a 0");
            labelErrorParticion.setVisible(true);
            return false;
        }
        return true;
    }
    
    @FXML
    void crearParticion(ActionEvent event) {
        if (validarCamposParticion()) {
            String nombre= txtNombreParticion.getText().trim();
            BigInteger tiempo = new BigInteger(txtTamanioParticion.getText());
            listaParticiones.add(new Particion(nombre, tiempo));
            if (listaParticiones.size() == 1) {
                comboBoxParticiones.getSelectionModel().selectFirst();
            }
            limpiarParticion(event);
        }
    }
    
    boolean validarCamposProceso(){
        if(txtNombreProceso.getText().replace(" ", "").isEmpty()){
            labelErrorProceso.setText("El nombre del proceso no puede estar vacío");
            labelErrorProceso.setVisible(true);
            return false;
        }
        if(listaProcesos.stream().anyMatch(e -> e.getNombre().equals(txtNombreProceso.getText()))){
            labelErrorProceso.setText("El nombre ya existe, por favor elija uno nuevo");
            labelErrorProceso.setVisible(true);
            return false;
        }
        if(txtTamanioProceso.getText().isEmpty()){
            labelErrorProceso.setText("El Tamaño del proceso no puede estar vacío");
            labelErrorProceso.setVisible(true);
            return false;
        }
        if(txtTiempoProceso.getText().isEmpty()){
            labelErrorProceso.setText("El Tiempo del proceso no puede estar vacío");
            labelErrorProceso.setVisible(true);
            return false;
        }
        if(txtTamanioProceso.getText().replaceAll("0+", "0").equals("0")){
            labelErrorProceso.setText("El Tamaño del proceso debe ser superior a 0");
            labelErrorProceso.setVisible(true);
            return false;
        }
        if(txtTiempoProceso.getText().replaceAll("0+", "0").equals("0")){
            labelErrorProceso.setText("El Tiempo del proceso debe ser superior a 0");
            labelErrorProceso.setVisible(true);
            return false;
        }
        if (listaParticiones.isEmpty()) {
            labelErrorProceso.setText("Debe asignar una particion, cree una primero");
            labelErrorProceso.setVisible(true);
            return false;
        }
        return true;
    }
    
    @FXML
    void crearProceso(ActionEvent event) {
        if (validarCamposProceso()) {
            String nombre= txtNombreProceso.getText().trim();
            BigInteger tiempo = new BigInteger(txtTiempoProceso.getText());
            BigInteger tamanio = new BigInteger(txtTamanioProceso.getText());
            Particion particion = comboBoxParticiones.getSelectionModel().getSelectedItem();
            Proceso proceso = new Proceso(nombre, tiempo, tamanio, particion);
            listaProcesos.add(proceso);
            particion.getProcesos().add(proceso);
            limpiarProceso(event);
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
    void validarNombreProceso(KeyEvent event) {
        if(listaProcesos.stream().anyMatch(e -> e.getNombre().equals(txtNombreProceso.getText()))){
            labelErrorProceso.setText("El nombre ya existe, por favor elija uno nuevo");
            labelErrorProceso.setVisible(true);
        }else {
            labelErrorParticion.setVisible(false);
        }
    }
    
    @FXML
    void validarNombreParticion(KeyEvent event) {
        if(listaParticiones.stream().anyMatch(e -> e.getNombre().equals(txtNombreParticion.getText()))){
            labelErrorParticion.setText("El nombre ya existe, por favor elija uno nuevo");
            labelErrorParticion.setVisible(true);
        }else {
            labelErrorParticion.setVisible(false);
        }
    }

    @FXML
    void iniciarProcesos(ActionEvent event) {
        if (listaProcesos.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No hay particiones");
            alert.setHeaderText("No existen particiones para la simulación");
            alert.setContentText("Asegurese de ingresar al menos una(1) particiones antes de iniciar la simulación");
            alert.initOwner(panelProcesos.getScene().getWindow());
            alert.showAndWait();
        }else{
            btnReportes.setDisable(false);
            listaReporte.clear();
            try {
                ap = new AdministradorProcesos(new ArrayList<>(listaParticiones));
                listaParticiones.forEach(e -> e.clear());
                ap.iniciarSecuencia();
                listaReporte.addAll(ap.getPilaProcesos());
                tabPaneParticiones.getTabs().clear();
                ArrayList<Particion> particiones = ap.getParticiones();
                for (Particion particion : particiones) {
                    tabPaneParticiones.getTabs().add(new TabParticion(particion));
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
        alert.setHeaderText("Software 4 de Sistemas Operativos");
        alert.setContentText("\nAutores:\n    *Julian David Grijalba Bernal\n    *William Desiderio Gil Farfan");
        alert.initOwner(panelProcesos.getScene().getWindow());
        alert.showAndWait();
    }

    @FXML
    void salir(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    public void eliminarProceso(ActionEvent event) {
        if (tablaProcesos.getSelectionModel().getSelectedIndex() >= 0) {
            Proceso proceso = listaProcesos.remove(tablaProcesos.getSelectionModel().getFocusedIndex());
            proceso.getParticion().getProcesos().remove(proceso);
        }else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No se puede eliminar");
            alert.setHeaderText("Debe seleccionar el proceso que desea eliminar");
            alert.initOwner(panelProcesos.getScene().getWindow());
            alert.showAndWait();
        }
    }
    
    @FXML
    public void eliminarParticion(ActionEvent event) {
        if (tablaParticiones.getSelectionModel().getSelectedIndex() >= 0) {
            listaParticiones.remove(tablaParticiones.getSelectionModel().getFocusedIndex());
        }else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No se puede eliminar");
            alert.setHeaderText("Debe seleccionar la particion que desea eliminar");
            alert.initOwner(panelProcesos.getScene().getWindow());
            alert.showAndWait();
        }
    }
    
    @FXML
    public void eliminarTodosLosParticion(ActionEvent event) {
        if (tablaParticiones.getItems().size() > 0) {
            Alert alert1 = new Alert(AlertType.CONFIRMATION);
            alert1.setTitle("Eliminar particiones");
            alert1.setHeaderText("¿Eliminar todas las particiones?");
            alert1.initOwner(panelProcesos.getScene().getWindow());
            alert1.showAndWait();
            if (alert1.getResult().getButtonData().isDefaultButton()) {
                tablaParticiones.getItems().clear();
            }
        }else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No se puede eliminar");
            alert.setHeaderText("No hay particiones registradas");
            alert.initOwner(panelProcesos.getScene().getWindow());
            alert.showAndWait();
        }
    }
    
    @FXML
    public void eliminarTodosLosProceso(ActionEvent event) {
        if (tablaProcesos.getItems().size() > 0) {
            Alert alert1 = new Alert(AlertType.CONFIRMATION);
            alert1.setTitle("Eliminar procesos");
            alert1.setHeaderText("¿Eliminar todos los procesos?");
            alert1.initOwner(panelProcesos.getScene().getWindow());
            alert1.showAndWait();
            if (alert1.getResult().getButtonData().isDefaultButton()) {
                tablaProcesos.getItems().clear();
            }
        }else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No se puede eliminar");
            alert.setHeaderText("No hay procesos registrados");
            alert.initOwner(panelProcesos.getScene().getWindow());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listaParticiones = FXCollections.observableArrayList();
        listaProcesos = FXCollections.observableArrayList();
        listaReporte = FXCollections.observableArrayList();
        
        final Particion particion1 = new Particion("pp1", new BigInteger("10"));
        listaParticiones.add(particion1);
        
        final Particion particion2 = new Particion("pp2", new BigInteger("10"));
        listaParticiones.add(particion2);
        
        final Proceso proceso1 = new Proceso("p1", new BigInteger("11"), new BigInteger("10"), particion2);
        listaProcesos.add(proceso1);
        final Proceso proceso2 = new Proceso("p2", new BigInteger("4"), new BigInteger("10"), particion2);
        listaProcesos.add(proceso2);
        final Proceso proceso3 = new Proceso("p3", new BigInteger("9"), new BigInteger("5"), particion1);
        listaProcesos.add(proceso3);
        final Proceso proceso4 = new Proceso("p4", new BigInteger("7"), new BigInteger("10"), particion1);
        listaProcesos.add(proceso4);
        
        particion2.getProcesos().add(proceso1);
        particion2.getProcesos().add(proceso2);
        particion1.getProcesos().add(proceso3);
        particion1.getProcesos().add(proceso4);
        
        tablaParticiones.setItems(listaParticiones);
        tablaProcesos.setItems(listaProcesos);
        tablaReportes.setItems(listaReporte);
        comboBoxParticiones.setItems(listaParticiones);
        
        txtTamanioParticion.textProperty().addListener(createChangeListener(txtTamanioParticion));
        txtTiempoProceso.textProperty().addListener(createChangeListener(txtTiempoProceso));
        txtTamanioProceso.textProperty().addListener(createChangeListener(txtTamanioProceso));
        
        txtTamanioParticion.setTooltip(new Tooltip("El tiempo del proceso debe ser superior a 0 y no puede estar vacío"));
    }
    
    private ChangeListener<String> createChangeListener(TextField textField) {
        return new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        };
    }

}
//<?import com.yuliamz.so.s4.Vista.*?>
