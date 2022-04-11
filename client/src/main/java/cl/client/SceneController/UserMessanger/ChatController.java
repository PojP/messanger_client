package cl.client.SceneController.UserMessanger;

import cl.client.DatabaseLogic.Database;
import cl.client.Package.requests.Message;
import cl.client.cipher.DES.DES;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class ChatController extends Pane {
    VBox MessagePane;
    ArrayList<MessageController> messages;
    private String password;

    /*
    //Creating the mouse event handler
    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {

        }
    };
    */
    @FXML
    private void ChatOpen(MouseEvent e) {
        GeneralWindow.CurrentChat=this;
        GeneralWindow.SetToMessagePane(messages);
        GeneralWindow.SetUserNameLabel(UserName);
    }

    @FXML
    private Label FirstCharOfNameMessageCloud;
    @FXML
    private Label NameMessageCloud;
    @FXML
    private Label LastMessageCloud;
    @FXML
    private Pane MessageCloud;
    @FXML
    private Label DataText;

    private String UserName;
    private String UserId;

    private void SetDate(String date) {
        DataText.setText(date);
    }

    private void SetUser(String usr) {
        FirstCharOfNameMessageCloud.setText(String.valueOf(usr.charAt(0)));
        NameMessageCloud.setText(usr);
    }

    private void setLastMessage(String Message) {
        LastMessageCloud.setText(Message);
    }
    public String GetLoginID()
    {
        return UserId;
    }
    private ArrayList<MessageController> DecipherMessages(ArrayList<MessageController> cipher_messages) {
        for (MessageController message : cipher_messages)
        {
            try {
                message.SetMessageText(DES.Decipher(message.GetMessageText(), password));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cipher_messages;
    }

    private void DecipherMessages() {
        for(MessageController message : messages)
        {
            try {
                System.out.println(password);
                message.SetMessageText(DES.Decipher(message.GetMessageText(), password.strip()));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isOpen() {
        return GeneralWindow.isOpen(messages);
    }
    private String GetPassword() {
        System.out.println(UserId);
        if (Database.CheckData("UserKeys", "user", "user", UserId)) {
            return Database.GetString("UserKeys", "key", "user", UserId);
        }
        return "no_pwd";
    }
    private void setAllData() {
        int lastIndex = messages.size()-1;
        MessageController lastMessage = messages.get(lastIndex);
        setLastMessage(lastMessage.GetMessageText());
        SetUser(UserName);
        SetDate(lastMessage.GetDataText());
    }
    protected String GetCipherMessage(String message)
    {
        try {
            System.out.println(password);
            return DES.Cipher(message,password.strip());
        } catch (Exception e) {
            e.printStackTrace();
            return "bad_msg";
        }
    }
    protected void UpdateData(ArrayList<MessageController> msgs) {
        msgs =DecipherMessages(msgs);
        if(isOpen()) {
            GeneralWindow.UpdateMessagePane(msgs);
        }
        messages.addAll(msgs);
    }

    public ChatController(ArrayList<MessageController> MESSAGES, String user, VBox pane, String userid) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/custom/chat.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
            MessagePane = pane;
            UserId = userid;
            UserName = user;
            messages = MESSAGES;
            password = GetPassword();
            DecipherMessages();
            setAllData();
    }
}
