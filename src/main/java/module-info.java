
module com.example.ador_testele {

    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.pdfbox;

    requires java.sql;
    requires org.postgresql.jdbc;
    requires org.junit.jupiter.api;
    requires java.desktop;
    requires boxable;

    opens com.example.ador_testele to javafx.fxml,javafx.graphics,javafx.controls;
    opens com.example.GUIController to javafx.fxml;
    opens com.example.Domain to javafx.base;


    exports com.example.ador_testele;
    exports com.example.GUIController;
    exports com.example.Domain;

}