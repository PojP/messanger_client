package cl.client.Package.requests;

import java.io.Serializable;
import java.util.ArrayList;

public class AllMessages implements Requestable, Serializable {
    private ArrayList<Message> Messages;
    public ArrayList<Message> GetMessages() {return Messages;}
    public AllMessages(ArrayList<Message> msgs) {Messages = msgs;}
    public AllMessages()
    {

    }

    @Override
    public RequestsTypes GetType() {
        return RequestsTypes.AllMessages;
    }
}
