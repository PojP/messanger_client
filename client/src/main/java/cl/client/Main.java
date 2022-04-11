package cl.client;

import cl.client.cipher.DES.DES;
import cl.client.DatabaseLogic.Database;
import cl.client.SceneController.UserPass.PassToMsgrController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import cl.client.client.ClientCore;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class Main extends Application {

    public static void main(String[] args) throws IOException, InterruptedException {
        Database db = new Database("src/main/resources/", "clientDB.db");
        db.OpenDB();
        new ClientCore("localhost", 9090);
        Application.launch();
        db.CloseDB();
    }
    @Override
    public void start (Stage MainStage) throws IOException {
            MainStage.setTitle("Messenger");
            MainStage.setResizable(false);
            System.out.println("Setting window...");
            PassToMsgrController genwindow = new PassToMsgrController();
            genwindow.FXMLLoad(MainStage);
            /*Parent loginSceneLoader = FXMLLoader.load(PassToMsgrController.class.getResource("/MainWindow.fxml"));
            Scene scene = new Scene(loginSceneLoader, Windows_Fields.HEIGHT, Windows_Fields.WIDTH);
            MainStage.setScene(scene);
            System.out.println("trying to show");
            MainStage.show();
            System.out.println("show successfully");
            //genwindow.FXMLLoad(MainStage);
            System.out.println("OK...");*/
    }
    }

/*
public class Main {
    public static void main(String[] args) {
        String cipher = null;
        try {
            cipher = DES.Cipher("Hello", "86");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String decipher = null;
        try {
            decipher = DES.Decipher(cipher, "86");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(cipher);
        System.out.println(decipher);

    }
}*/