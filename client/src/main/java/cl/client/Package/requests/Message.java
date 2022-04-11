package cl.client.Package.requests;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Requestable, Serializable {
    private ArrayList<String> Message;
    public ArrayList<String> GetMessage() {return Message;}
    public Message(ArrayList<String> DATA_MSG) {
        if (DATA_MSG.size()!=4)
        {
            throw new ArrayIndexOutOfBoundsException();
        }
        Message = DATA_MSG;} //user,to_user,date,ciphered_message

    @Override
    public RequestsTypes GetType() {
        return RequestsTypes.Message;
    }
}
