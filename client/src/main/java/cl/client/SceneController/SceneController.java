package cl.client.SceneController;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    protected Stage stage;
    protected Scene scene;

    protected void ChangeScene(String pathToFXML_Scene, Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(pathToFXML_Scene));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    protected void ChangeScene(String pathToFXML_Scene, Stage _stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(pathToFXML_Scene));
        stage = _stage;
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
