package cl.client.Package.requests;

import java.io.Serializable;

public class Registrate implements Requestable, Serializable {
    RequestsTypes Type = RequestsTypes.Registrate;
    private String login;
    private String loginName;
    private String HashedPWD;
    public RequestsTypes GetType() {
        return Type;
    }
    public Registrate(String lg, String lgn, String pwd) {
        this.login = lg;
        this.loginName = lgn;
        this.HashedPWD = pwd;
    }

    public String GetLogin() {
        return login;
    }

    public String GetLName() {
        return loginName;
    }

    public String GetPWD() {
        return HashedPWD;
    }
}
