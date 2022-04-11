package cl.client.Package.requests;

import java.io.Serializable;

public class Login implements Requestable, Serializable {
    RequestsTypes Type = RequestsTypes.Login;
    private String login;
    private String HashedPWD;
    public RequestsTypes GetType() {
        return Type;
    }
    public Login(String lg, String pwd) {
        this.login = lg;
        this.HashedPWD = pwd;
    }

    public String GetPWD() {
        return HashedPWD;
    }

    public String GetLogin() {
        return login;
    }
}

