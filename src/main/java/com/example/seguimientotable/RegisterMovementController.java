package com.example.seguimientotable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class RegisterMovementController implements Initializable {

    @FXML
    private Button backBT;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField descriptionTF;

    @FXML
    private Label messageLB;

    @FXML
    private TextField montoTF;

    @FXML
    private Button registerBT;

    @FXML
    private ComboBox<String> tipoCB;
    @FXML
    public void register(ActionEvent actionEvent) {
        try{
            double monto = Double.parseDouble(montoTF.getText());
            String descripcion = descriptionTF.getText();
            MovementType tipo = calculateType();
            String date = calculateDate();
            if(tipo==null || date==null){
                return;
            }
            MovementList.getInstance().getMovements().add(new Movement(monto,descripcion,date,tipo));
            HelloApplication.saveData();
            HelloApplication.openWindow("bankTable.fxml");
            Stage stage = (Stage)backBT.getScene().getWindow();
            stage.close();
        }catch(NumberFormatException | ParseException ex){
            messageLB.setText("Invalid Input value");
        }

    }
    private MovementType calculateType(){
        MovementType tipo = null;
        if(tipoCB.getValue()==null){
            messageLB.setText("Invalid Type");
            return null;
        }
        if(tipoCB.getValue().equals("INGRESO")){
            tipo = MovementType.INGRESO;
        }else if(tipoCB.getValue().equals("GASTO")){
            tipo = MovementType.GASTO;
        }
        return tipo;
    }
    private String calculateDate() throws ParseException {
        try{
            int dayNum = datePicker.getValue().getDayOfMonth();
            String day;
            if(dayNum<10){
                day = "0"+dayNum;
            }else{
                day = ""+dayNum;
            }
            int monthNum = datePicker.getValue().getMonthValue();
            String month;
            if(monthNum<10){
                month = "0"+monthNum;
            }else{
                month = ""+monthNum;
            }
            int yearNum = datePicker.getValue().getYear();
            String year = ""+datePicker.getValue().getYear();

            String fecha = day+"/"+month+"/"+year;

            //No entendí como funciona el DateFormat por lo que mostraré la fecha como String

            return fecha;
        }catch(Exception ex){
            messageLB.setText("Invalid Date");
            return null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backBT.setOnAction(action ->{
            HelloApplication.openWindow("bankTable.fxml");
            Stage stage = (Stage)backBT.getScene().getWindow();
            stage.close();
        });
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("INGRESO");
        list.add("GASTO");
        tipoCB.setItems(list);
    }

}
