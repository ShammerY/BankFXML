package com.example.seguimientotable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;

public class BankTableController implements Initializable {
    @FXML
    public TableColumn<?, ?> tipoTC;
    @FXML
    public Label balanceLB;
    @FXML
    private TableColumn<?, ?> descriptcionTC;
    @FXML
    private TableColumn<?, ?> fechaTC;
    @FXML
    private Button ingresoBT;
    @FXML
    private TableColumn<?, ?> montoTC;
    @FXML
    private TableView<Movement> movementTable;
    @FXML
    private Button registerMovementBT;
    @FXML
    private Button retiroBT;
    @FXML
    private Button todoBT;
    @FXML
    void orderGastos(ActionEvent event) {
        ObservableList<Movement> list = MovementList.getInstance().getMovements();
        ObservableList<Movement> array = FXCollections.observableArrayList();
        for(Movement m:list){
            if(m.getTipo().equals(MovementType.GASTO)){
                array.add(m);
            }
        }
        movementTable.setItems(sortDescendingItems(array));
    }

    @FXML
    void orderIngresos(ActionEvent event) {
        ObservableList<Movement> list = MovementList.getInstance().getMovements();
        ObservableList<Movement> array = FXCollections.observableArrayList();
        for(Movement m:list){
            if(m.getTipo().equals(MovementType.INGRESO)){
                array.add(m);
            }
        }
        movementTable.setItems(sortDescendingItems(array));
    }
    @FXML
    public void setTableItems(){
        movementTable.setItems(sortDescendingItems(MovementList.getInstance().getMovements()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        montoTC.setCellValueFactory(new PropertyValueFactory<>("monto"));
        descriptcionTC.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        fechaTC.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tipoTC.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        setTableItems();
        registerMovementBT.setOnAction(action ->{
            HelloApplication.openWindow("registerMovement.fxml");
            Stage stage = (Stage)registerMovementBT.getScene().getWindow();
            stage.close();
        });
        balanceLB.setText(calculateBalance());
    }
    private String calculateBalance(){
        ObservableList<Movement> list = MovementList.getInstance().getMovements();
        double income = 0;
        double spences = 0;
        for(Movement m:list){
            if (m.getTipo().equals(MovementType.INGRESO)){
                income += m.getMonto();
            }
            if(m.getTipo().equals(MovementType.GASTO)){
                spences += m.getMonto();
            }
        }
        return "$"+(income-spences);
    }

    private ObservableList<Movement> sortItems(ObservableList<Movement> list){
        Collections.sort(list,(a,b) ->{
            Date dateA = fromStringToDate(a.getFecha());
            Date dateB = fromStringToDate(b.getFecha());
            if((dateA.getTime()-dateB.getTime())>0){
                return 1;
            }else if((dateA.getTime()-dateB.getTime())<0){
                return -1;
            }else{
                return 0;
            }
        });
        return list;
    }
    private ObservableList<Movement> sortDescendingItems(ObservableList<Movement> list){
        Collections.sort(list,(a,b) ->{
            Date dateA = fromStringToDate(a.getFecha());
            Date dateB = fromStringToDate(b.getFecha());
            if((dateA.getTime()-dateB.getTime())<0){
                return 1;
            }else if((dateA.getTime()-dateB.getTime())>0){
                return -1;
            }else{
                return 0;
            }
        });
        return list;
    }
    @FXML
    public void sortDateColumn(SortEvent<TableView<Movement>> actionEvent) {
        ObservableList<Movement> list = movementTable.getItems();
        if(fechaTC.getSortType().equals(TableColumn.SortType.ASCENDING)){
            System.out.println("Sorteando ascendido");
            movementTable.setItems(sortItems(list));
        }else if(fechaTC.getSortType().equals(TableColumn.SortType.DESCENDING)){
            System.out.println("Sorteando Descendido");
            movementTable.setItems(sortDescendingItems(list));
        }
    }
    private Date fromStringToDate(String fecha){
        String[] content = fecha.split("/");
        int day = Integer.parseInt(content[0]);
        int month = Integer.parseInt(content[1]);
        int year = Integer.parseInt(content[2]);
        Date date = new Date(year,month,day);
        return date;
    }
}
