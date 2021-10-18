module com.desktop.pizzasystemdekstop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens com.desktop.pizzasystemdekstop;
    exports com.desktop.pizzasystemdekstop;
}