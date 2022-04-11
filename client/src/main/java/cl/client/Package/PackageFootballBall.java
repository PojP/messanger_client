package cl.client.Package;

import cl.client.Package.requests.Requestable;

import java.io.Serializable;

public class PackageFootballBall implements Serializable
{
    private final Requestable Request;
    public PackageFootballBall(Requestable req)
    {

        Request = req;
    }
    public Requestable GetReq()
    {
        return Request;
    }
}
