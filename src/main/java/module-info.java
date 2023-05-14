module com.example.seguimientotable {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.seguimientotable to javafx.fxml;
    exports com.example.seguimientotable;
}