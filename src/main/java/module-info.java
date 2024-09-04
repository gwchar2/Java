module org.example.maman13 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires java.logging;
    requires jdk.jdi;
    requires java.sql;

    opens DrawApp to javafx.fxml;
    exports DrawApp;
    opens FourInARow to javafx.fxml;
    exports FourInARow;
}