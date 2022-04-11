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
import java.util.ArrayList;

public class RegistrationController extends SceneController {

    @FXML
    private TextField NickNameField;
    @FXML
    private TextField LoginField;
    @FXML
    private PasswordField PasswordFirstField;
    @FXML
    private PasswordField PasswordSecondField;
    @FXML
    private Label BadText;
    @FXML
    private Label InfoText;


    @FXML
    private void OnNicknameClicked()
    {
        System.out.println("clicked");

        InfoText.setText("Nickname size requires under 20 symbols");
        InfoText.setVisible(true);
    }
    @FXML
    private void OnLoginClicked()
    {
        System.out.println("clicked");

        InfoText.setText("Login size requires from 7 to 15 symbols");
        InfoText.setVisible(true);
    }
    @FXML
    private void OnPasswordClicked()
    {
        System.out.println("clicked");
        InfoText.setText("Password size requires under from 12 to 20 symbols");
        InfoText.setVisible(true);
    }

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
    private void CheckEnteredData(ActionEvent e)
    {
        //get text or lenght from fields
        String loginText = LoginField.getText();
        int LenghtFPwd = PasswordFirstField.getLength();
        int LenghtSPwd = PasswordSecondField.getLength();
        String nicknameText = NickNameField.getText();

        //set all rules for checking
        boolean LoginSize= loginText.length()<15 & loginText.length()>7;
        boolean UserNameSize=  nicknameText.length()<20;
        boolean isEqPWDS= LenghtFPwd==LenghtSPwd;
        boolean HashPWD_Size=LenghtFPwd<20 & LenghtFPwd>12;

        //if all rules are true
        if( LoginSize & UserNameSize & isEqPWDS & HashPWD_Size)
        {
            ClientCore.RequestRegistrationSender(loginText, nicknameText,PasswordFirstField.getText());
            String Token = ClientCore.ResponseTokenHandler();
            //String Token = null;
            if(Token==null)
            {
                SetBadText("There is not such account");
                SetErrWindow();
            }else
            {
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
                    ArrayList<String> rows = new ArrayList<String>();
                    ArrayList<String> data = new ArrayList<String>();

                    rows.add("sess");
                    rows.add("User");
                    data.add(Token);
                    data.add(loginText);
                    Database.WriteToDB("UserToken", rows, data);
                }
            }
        }
        else
        {
            SetBadText("Bad entered data");
            SetErrWindow();
        }

    }
}
