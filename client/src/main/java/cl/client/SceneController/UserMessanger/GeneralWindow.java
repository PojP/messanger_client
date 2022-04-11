package cl.client.SceneController.UserMessanger;

import cl.client.DatabaseLogic.Database;
import cl.client.Package.requests.Message;
import cl.client.SceneController.SceneController;
import cl.client.client.ClientCore;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class GeneralWindow extends SceneController implements Initializable {
    @FXML
    Label NicknameLabel;
    static Label usernameLabel;
    @FXML
    VBox MessageCloudBox;
    @FXML
    Pane MessageCloud;
    @FXML
    VBox MessageScrollPane;
    static VBox MessageScroll;
    @FXML
    ScrollPane AccountScrollPane;
    @FXML
    AnchorPane FrameworkOn;
    @FXML
    TextArea EnterTextField;
    protected static ChatController CurrentChat;
    HashMap<String, ChatController> chats;
    HashMap<String, String>         loginsAndNicknames;

    protected static String LOGIN;

    protected static void SetUserNameLabel(String userName) {
        usernameLabel.setText(userName);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LOGIN = Database.GetString("UserToken", "User", "id", "1").strip();
        chats = new HashMap<>();
        loginsAndNicknames = new HashMap<>();
        MessageScroll = MessageScrollPane;
        usernameLabel= NicknameLabel;
        CreateChats();
    }
    private String SetLogin(Message msg)
    {
        System.out.println("MESSAGE:: "+msg.GetMessage());
        String login = msg.GetMessage().get(0);


        if(LOGIN.contains(login))
        {
            return msg.GetMessage().get(1);
        }
        return login;
    }

    private String GetNickname(String login)
    {
        String nickname = "";
        if(loginsAndNicknames.containsKey(login)) {
            nickname = loginsAndNicknames.get(login);
        }
        else
        {
            ClientCore.RequestNicknameSender(login);
            nickname = ClientCore.ResponseNicknameHandler();
            if(nickname==null | nickname=="") {nickname="???USER??";}
            else {
                loginsAndNicknames.put(login, nickname);
            }
        }
        return nickname;
    }



    @FXML
    private void TextTyped(KeyEvent event)
    {
        String message = EnterTextField.getText();
        if (message != "" & event.getCode() == event.getCode().ENTER & CurrentChat!=null) {
            //CreateChats();
            ArrayList<String> data = new ArrayList<String>();
            data.add(LOGIN);
            data.add(CurrentChat.GetLoginID());
            System.out.println(message);
            data.add("");
            data.add(CurrentChat.GetCipherMessage(message));
            Message smess = new Message(data);
            ClientCore.RequestMessageSender(smess);
            CreateChats();
        }
        else if(event.getCode() == event.getCode().F5) {
            CreateChats();
        }
        else if(event.getCode() == event.getCode().F4)
        {
            try {
                ChangeScene("/user_messanger/CreateNewDialog.fxml", event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }
    private void CreateChats() {
        ClientCore.RequestAllMessagesSender();
        ArrayList<Message> messageArrayList = ClientCore.ResponseAllMessagesHandler();
        if (messageArrayList != null) {
            if (!messageArrayList.isEmpty()) {
                System.out.println("MESSAGEARRAYLIST:: " + messageArrayList);
                for (Message msg : messageArrayList) {
                    //setting user id
                    String log = SetLogin(msg);
                    System.out.println("LOGIN:: " + log);
                    //if chat with this user was created
                    if (chats.containsKey(log)) {
                        //this part of algorithm add new messages to chat
                        ArrayList<MessageController> AppendedMessagesToNewChat = new ArrayList<>();

                            if (LOGIN.contains(msg.GetMessage().get(1).strip())) {
                                AppendedMessagesToNewChat.add(new MessageController(msg.GetMessage().get(3), true, msg.GetMessage().get(2)));
                            } else if (LOGIN.contains(msg.GetMessage().get(0).strip())) {
                                AppendedMessagesToNewChat.add(new MessageController(msg.GetMessage().get(3), false, msg.GetMessage().get(2)));
                            }

                        chats.get(log).UpdateData(AppendedMessagesToNewChat);

                        //set new chat as old and already created for next checks
                    } else {
                        ArrayList<MessageController> AppendedMessagesToNewChat = new ArrayList<>();
                        if (LOGIN.contains(msg.GetMessage().get(1).strip())) {
                            AppendedMessagesToNewChat.add(new MessageController(msg.GetMessage().get(3), true, msg.GetMessage().get(2)));
                        } else if (LOGIN.contains(msg.GetMessage().get(0).strip())) {
                            AppendedMessagesToNewChat.add(new MessageController(msg.GetMessage().get(3), false, msg.GetMessage().get(2)));
                        }

                        ChatController chat = new ChatController(AppendedMessagesToNewChat, GetNickname(log), MessageScroll, log);
                        chats.put(log, chat);
                        MessageCloudBox.getChildren().add(chat);
                    }
                }
            }
        }
    }
    protected static void SetToMessagePane(ArrayList<MessageController> messages)
    {
        MessageScroll.getChildren().clear();
        MessageScroll.getChildren().addAll(messages);
    }
    protected static void UpdateMessagePane(ArrayList<MessageController> messages)
    {
        MessageScroll.getChildren().addAll(messages);
    }
    protected static boolean isOpen(ArrayList<MessageController> messages)
    {
        if(MessageScroll.getChildren().containsAll(messages))
        {
            return true;
        }
        return false;
    }
}
