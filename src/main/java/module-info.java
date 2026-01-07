module com.example.test2sda {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.test2sda to javafx.fxml;
    exports com.example.test2sda;
}