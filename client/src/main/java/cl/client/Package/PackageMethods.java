package cl.client.Package;

import cl.client.Package.requests.Registrate;
import cl.client.Package.requests.Requestable;

import java.io.*;
import java.net.Socket;

public class PackageMethods {
    private ObjectOutputStream oostream;
    private InputStream inputStream;
    private ObjectInputStream oistream;
    private OutputStream outputStream;
    public PackageMethods(Socket socket) throws IOException {
        inputStream  =socket.getInputStream();
        outputStream =socket.getOutputStream();

        oostream = new ObjectOutputStream(outputStream);
        oistream = new ObjectInputStream(inputStream);
    }

    public static PackageFootballBall CreatePackage(Requestable Req)
    {
        PackageFootballBall CrPackage = new PackageFootballBall(Req);
        return CrPackage;
    }
    public void SendSerializedPackage(Requestable Req)
    {

        System.out.println("Sending messages to the ServerSocket");
        try {
            oostream.writeObject(new PackageFootballBall(Req));
            oostream.flush();
            oostream.reset();
            System.out.println("successfully");
        } catch (IOException e) {
            System.out.println("SEND ERR");
            e.printStackTrace();
        }
    }
    public Requestable ReadDeserializedPackage()
    {
        try {
            PackageFootballBall pack = (PackageFootballBall) oistream.readObject();
            return pack.GetReq();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
