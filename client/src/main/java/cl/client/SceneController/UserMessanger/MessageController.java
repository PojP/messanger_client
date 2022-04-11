package cl.client.SceneController.UserMessanger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.io.IOException;

public class MessageController extends HBox {
    @FXML private VBox GeneralMessageBox;
    @FXML private Label MessageText;
    @FXML private HBox MainHBox;
    @FXML private Label DataText;
    private void  SetNotMyMessage(boolean Type)
    {
        if(Type)
        {
            MainHBox.setAlignment(Pos.CENTER_LEFT);
            GeneralMessageBox.setStyle("-fx-background-color: #c5c5c5; -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10;");
        }
        else {
            MainHBox.setAlignment(Pos.CENTER_RIGHT);
            GeneralMessageBox.setStyle("-fx-background-color: #adcfe6; -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10;");
        }
    }
    protected void SetMessageText(String text) {MessageText.setText(text);}
    public String GetMessageText() {return MessageText.getText();}
    public String GetDataText() {return DataText.getText();}
    public MessageController(String text, boolean message_type, String date) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/custom/message.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        //setMargin(this, new Insets(3,0,3,0));
        DataText.setText(date);
        DataText.setTextFill(Paint.valueOf("#1b1b1b"));
        GeneralMessageBox.setPadding(new Insets(5, 5, 5, 5));
        SetNotMyMessage(message_type);
        MessageText.setText(text);
    }
}
