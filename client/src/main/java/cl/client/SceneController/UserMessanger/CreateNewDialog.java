package cl.client.SceneController.UserMessanger;

import cl.client.DatabaseLogic.Database;
import cl.client.Package.requests.Message;
import cl.client.SceneController.SceneController;
import cl.client.cipher.DES.DES;
import cl.client.client.ClientCore;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.ArrayList;

import static cl.client.SceneController.UserMessanger.GeneralWindow.LOGIN;

public class CreateNewDialog extends SceneController {
    @FXML
    PasswordField Password;
    @FXML
    TextArea Message;
    @FXML
    TextField Login;

    @FXML
    private void EnterText(KeyEvent event)
    {
        boolean LoginSize=    Login.getText().length()<15 & Login.getText().length()>7;
        boolean MessageSize=  Message.getText()!="";
        boolean PasswordSize= Password.getText().length()>5;
        if(LoginSize & MessageSize & PasswordSize & event.getCode()==event.getCode().ENTER)
        {
            ArrayList<String> data = new ArrayList<String>();
            data.add(LOGIN);
            data.add(Login.getText());
            data.add("");
            try {
                data.add(DES.Cipher(Message.getText(), Password.getText()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            cl.client.Package.requests.Message smess = new Message(data);
            ClientCore.RequestMessageSender(smess);
            ArrayList<String> added_rows_data = new ArrayList<>();
            added_rows_data.add("user");
            added_rows_data.add("key");
            ArrayList<String> added_data = new ArrayList<>();
            added_data.add(Login.getText());
            added_data.add(Password.getText());
            if(Database.CheckData("Userkeys", "user", "user", Login.getText()))
            {
                Database.UpdateDB("UserKeys", "key",Password.getText(),"user", Login.getText());
            }
            else {
                Database.WriteToDB("UserKeys", added_rows_data, added_data);
            }
            try {
                System.out.println("Redirecting to general window...");
                ChangeScene("/user_messanger/GeneralWindow.fxml",event);

            } catch (IOException e) {
                System.out.println("Fail in redirecting");
                e.printStackTrace();
            }
        }
    }
}
