module com.example.tubes_proglan {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.tubes_proglan to javafx.fxml;
    exports com.example.tubes_proglan;
}