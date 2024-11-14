module com.example.l {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.l to javafx.fxml;
    exports com.example.l;
}