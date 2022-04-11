package cl.client.SceneController.UserPass;
import cl.client.DatabaseLogic.Database;
import cl.client.Main;
import cl.client.Package.requests.Message;
import cl.client.SceneController.SceneController;
import cl.client.client.ClientCore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PassToMsgrController extends SceneController {
    public void FXMLLoad(Stage stage) throws IOException {
        ChangeScene("/MainWindow.fxml", stage);
        initialize(stage);
    }

    private void initialize(Stage _stage)
    {
        String token = Database.GetString("UserToken", "sess", "id", "1");
        if (token!=null & !token.equals(""))
        {
            System.out.println("TOKEN: " + token);
            ClientCore.RequestLoginSender(token);
            if(ClientCore.ResponseLoginHandler())
            {
                try {
                    ChangeScene("/user_messanger/GeneralWindow.fxml", _stage);
                } catch (IOException e) {
                    e.printStackTrace();
                    //System.out.println("coul");
                }
            }
            else
            {
                System.out.println("BAD TOKEN");
            }
        }
        else
        {
            System.out.println("BAD TOKEN");
        }
    }

    @FXML
    private void LoginUser(ActionEvent Aevent) throws IOException
    {
        //ChangeScene("/user_messanger/GeneralWindow.fxml", Aevent);
        ChangeScene("/user_pass/LoginWindow.fxml", Aevent);
    }
    @FXML
    public void RegistrationUser(ActionEvent Aevent) throws IOException {
        ChangeScene("/user_pass/RegistrationWindow.fxml", Aevent);
    }
}
