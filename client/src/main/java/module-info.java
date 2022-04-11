module cl.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires com.google.common;
    opens cl.client.SceneController.UserPass to javafx.fxml;
    opens cl.client.SceneController.UserMessanger to javafx.fxml;
    opens cl.client to javafx.fxml, javafx.graphics;
}