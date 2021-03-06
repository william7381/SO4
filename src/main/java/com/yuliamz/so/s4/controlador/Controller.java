package com.yuliamz.so.s4.controlador;

import com.yuliamz.so.s4.Modelo.AdministradorProcesos;
import com.yuliamz.so.s4.Modelo.Particion;
import com.yuliamz.so.s4.Modelo.Proceso;
import com.yuliamz.so.s4.Vista.TabParticion;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
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
    private Thread thread;
    private BigInteger tiempoGeneral = BigInteger.ZERO;

    @FXML private Pane panelProcesos, panelReportes, panelCargando;
    @FXML private TextField txtNombreParticion, txtTamanioParticion, txtNombreProceso, txtTiempoProceso, txtTamanioProceso, txtBuscarParticion;
    @FXML private ComboBox<Particion> comboBoxParticiones;
    @FXML private Label labelErrorParticion, labelErrorProceso, labelErrorBuscarParticion;
    @FXML private Button btnReportes;
    @FXML private TableView<Proceso> tablaProcesos;
    @FXML private TableView<Particion> tablaParticiones;
    @FXML private TableView<Proceso> tablaReportes;
    @FXML private TabPane tabPaneParticiones;
    
    @FXML
    void buscarParticion(ActionEvent event) {
        String text = txtBuscarParticion.getText();
        Stream<Tab> filter = tabPaneParticiones.getTabs().stream().filter(e -> e.getText().equals(text));
        try {
            Tab tab = filter.findFirst().get();
            tabPaneParticiones.getSelectionModel().select(tab);
            labelErrorBuscarParticion.setVisible(false);
        } catch (Exception e) {
            labelErrorBuscarParticion.setVisible(true);
        }
    }
    
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
        labelErrorProceso.setVisible(false);
    }

    boolean validarCamposParticion(){
        if(txtNombreParticion.getText().replace(" ", "").isEmpty()){
            labelErrorParticion.setText("El nombre de la particion no puede estar vacío");
            labelErrorParticion.setVisible(true);
            return false;
        }
        if(listaParticiones.stream().anyMatch(e -> e.getNombre().equals(txtNombreParticion.getText()))){
            labelErrorParticion.setText("El nombre ya existe, por favor elija uno nuevo");
            labelErrorParticion.setVisible(true);
            return false;
        }
        if(txtTamanioParticion.getText().isEmpty()){
            labelErrorParticion.setText("El tiempo de la particion no puede estar vacío");
            labelErrorParticion.setVisible(true);
            return false;
        }
        if(txtTamanioParticion.getText().replaceAll("0+", "0").equals("0")){
            labelErrorParticion.setText("El tiempo de la particion debe ser superior a 0");
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
            tiempoGeneral = tiempoGeneral.add(tiempo);
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
            labelErrorProceso.setVisible(false);
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
    void cancelarProcesos(ActionEvent event) {
        try {thread.stop();} catch (Exception e) {}
        btnReportes.setDisable(true);
        panelCargando.setVisible(false);
        mostrarProcesos();
    }
    
    @FXML
    void iniciarProcesos(ActionEvent event) {
        if (listaParticiones.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No hay particiones");
            alert.setHeaderText("No existen particiones para la simulación");
            alert.setContentText("Debe ingresar al menos una(1) particion");
            alert.initOwner(panelProcesos.getScene().getWindow());
            alert.showAndWait();
        }else{
            if (tiempoGeneral.compareTo(new BigInteger("10000000")) >= 0) {
                mostrarPanelCargando();
            }
            this.thread = new Thread(() -> {
                try {
                    listaReporte.clear();
                    ap = new AdministradorProcesos(new ArrayList<>(listaParticiones));
                    listaParticiones.forEach(e -> e.clear());
                    ap.iniciarSecuencia();
                    listaReporte.addAll(ap.getPilaProcesos());
                    mostrarReporteEnTabs();
                    mostrarReportes();
                    btnReportes.setDisable(false);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error inesperado");
                    alert.setHeaderText("Ocurrió un error en la ejecución");
                    alert.setContentText("Asegurese que la información insertada sea correcta");
                    alert.initOwner(panelProcesos.getScene().getWindow());
                    alert.showAndWait();
                }
            });
            thread.start();
        }
    }

    private void mostrarReporteEnTabs() {
        Platform.runLater(() -> {
            tabPaneParticiones.getTabs().clear();
        });
        ArrayList<Particion> particiones = ap.getParticiones();
        for (Particion particion : particiones) {
            Platform.runLater(() -> {
                tabPaneParticiones.getTabs().add(new TabParticion(particion, tabPaneParticiones));
            });
        }
        TabParticion tabParticion = new TabParticion("Total");
        tabParticion.crearDesde(new ArrayList<Proceso>(this.listaProcesos), this.ap.getTotalProcesados(), this.ap.getTotalNoProcesados());
        Platform.runLater(() -> {
            tabPaneParticiones.getTabs().add(tabParticion);
        });
    }

    void mostrarReportes() {
        panelCargando.setVisible(false);
        panelProcesos.setVisible(false);
        panelReportes.setVisible(true);
    }
    
    void mostrarPanelCargando() {
        panelReportes.setVisible(false);
        panelProcesos.setVisible(false);
        panelCargando.setVisible(true);
    }

    void mostrarProcesos() {
        panelCargando.setVisible(false);
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
            tiempoGeneral = tiempoGeneral.subtract(proceso.getTiempo());
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
            Particion particion = listaParticiones.remove(tablaParticiones.getSelectionModel().getFocusedIndex());
            for (Proceso proceso : particion.getProcesos()) {
                listaProcesos.remove(proceso);
                tiempoGeneral = tiempoGeneral.subtract(proceso.getTiempo());
            }
        }else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No se puede eliminar");
            alert.setHeaderText("Debe seleccionar la particion que desea eliminar");
            alert.initOwner(panelProcesos.getScene().getWindow());
            alert.showAndWait();
        }
    }
    
    @FXML
    public void eliminarTodasLasParticiones(ActionEvent event) {
        if (tablaParticiones.getItems().size() > 0) {
            Alert alert1 = new Alert(AlertType.CONFIRMATION);
            alert1.setTitle("Eliminar particiones");
            alert1.setHeaderText("¿Eliminar todas las particiones?");
            alert1.initOwner(panelProcesos.getScene().getWindow());
            alert1.showAndWait();
            if (alert1.getResult().getButtonData().isDefaultButton()) {
                listaProcesos.clear();
                listaParticiones.clear();
            }
            tiempoGeneral = BigInteger.ZERO;
        }else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No se puede eliminar");
            alert.setHeaderText("No hay particiones registradas");
            alert.initOwner(panelProcesos.getScene().getWindow());
            alert.showAndWait();
        }
    }
    
    @FXML
    public void eliminarTodosLosProcesos(ActionEvent event) {
        if (tablaProcesos.getItems().size() > 0) {
            Alert alert1 = new Alert(AlertType.CONFIRMATION);
            alert1.setTitle("Eliminar procesos");
            alert1.setHeaderText("¿Eliminar todos los procesos?");
            alert1.initOwner(panelProcesos.getScene().getWindow());
            alert1.showAndWait();
            if (alert1.getResult().getButtonData().isDefaultButton()) {
                for (Particion particion : listaParticiones) {
                    particion.getProcesos().clear();
                }
                listaProcesos.clear();
            }
            tiempoGeneral = BigInteger.ZERO;
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
