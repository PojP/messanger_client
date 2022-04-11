package cl.client.Package.requests;

import java.io.Serializable;

public class LogByToken implements Requestable, Serializable {
    private String Token;
    public String GetToken() {return Token;}
    public LogByToken(String token) {Token = token;}
    @Override
    public RequestsTypes GetType() {
        return RequestsTypes.LogByToken;
    }
}
