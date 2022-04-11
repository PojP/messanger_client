package cl.client.Package.requests;

import java.io.Serializable;

public class Nickname implements Requestable, Serializable {
    private String Login;
    private String Nickname;
    @Override
    public RequestsTypes GetType() {
        return RequestsTypes.Nickname;
    }
    public Nickname(String login)
    {
        Login = login;

    }
    public Nickname(String login, String nickname)
    {
        Login = login;
        Nickname  = nickname;
    }
    public String getNickname()
    {
        return Nickname;
    }
    public String getLogin()
    {
        return Login;
    }
}
