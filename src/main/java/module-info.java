module com.mycompany.smartride {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.smartride to javafx.fxml;
    exports com.mycompany.smartride;
}
