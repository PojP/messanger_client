package cl.client.Package.requests;

import java.io.Serializable;

public class Token implements Requestable, Serializable {
    private String Token;
    RequestsTypes Type = RequestsTypes.Token;
    public RequestsTypes GetType() { return Type;}
    public String GetToken() {
        return Token;
    }

    public Token(String s_tk) {
        Token = s_tk;
    }
}