package cl.client.SceneController.UserPass;

import cl.client.DatabaseLogic.Database;
import cl.client.SceneController.SceneController;
import cl.client.client.ClientCore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;



public class LoginController extends SceneController {
    @FXML
    private TextField LoginField;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private Label BadText;

    @FXML
    private void CancelButton(ActionEvent Aevent) throws IOException
    {
        System.out.println("Cancel");
        ChangeScene("/MainWindow.fxml", Aevent);
    }
    @FXML
    private void EnterButton(ActionEvent Aevent)
    {
        CheckEnteredData(Aevent);
    }
    private void SetBadText(String Text)
    {
        BadText.setText(Text);
    }
    private void SetErrWindow()
    {
        BadText.setVisible(true);
    }
    private boolean CheckEnteredData(ActionEvent e)
    {
        String loginText = LoginField.getText();
        int LenghtPwd = PasswordField.getLength();

        boolean LoginSize= loginText.length()<15 & loginText.length()>7;
        boolean HashPWD_Size=LenghtPwd<20 & LenghtPwd>12;

        if( LoginSize & HashPWD_Size )
        {
            ClientCore.RequestLoginSender(loginText,PasswordField.getText());
            String Token = ClientCore.ResponseTokenHandler();
            if(Token==null)
            {
                SetBadText("There is not such account");
                SetErrWindow();
            }
            else {
                try {
                    ChangeScene("/user_messanger/GeneralWindow.fxml", e);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if(Database.CheckData("UserToken", "sess", "id", "1"))
                {
                    Database.UpdateDB("UserToken", "sess", Token, "id", "1");
                }
                else
                {
                    Database.WriteToDB("UserToken", "sess", Token);
                }
            }
        }
        else
        {
            SetBadText("Bad entered data");
            SetErrWindow();
        }


        return false;
    }
}
