package cl.client.client;

import cl.client.Package.PackageMethods;
import cl.client.Package.requests.*;
import java.io.*;
import com.google.common.hash.Hashing;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ClientCore {
    private static Socket clientSocket;
    private static PackageMethods PackageBuilder;

    public ClientCore(String _socket, int _port) {
        try {
            clientSocket = new Socket(_socket, _port);
            PackageBuilder = new PackageMethods(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void TerminateClient()
    {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void RequestRegistrationSender(String login, String userName, String pwd) {
        PackageBuilder.SendSerializedPackage(new Registrate(login, userName, getHashedPWD(pwd)));
    }
    public static void RequestLoginSender(String login, String pwd)
    {
        PackageBuilder.SendSerializedPackage(new Login(login,pwd));
    }
    public static void RequestLoginSender(String Token)
    {
        PackageBuilder.SendSerializedPackage(new LogByToken(Token));
    }
    public static boolean ResponseLoginHandler()
    {
        Requestable request = PackageBuilder.ReadDeserializedPackage();
        System.out.println(request.GetType());
        switch (request.GetType()) {
            case GOOD_REQ:
                GOOD_REQ good = (GOOD_REQ) request;
                System.out.println(good.GetStackTrace());
                return true;
            case BAD_REQ:
                BAD_REQ bad = (BAD_REQ) request;
                System.out.println(bad.GetStackTrace());
                return false;
            case default:
                return false;
        }
    }
    public static String ResponseTokenHandler(){
        Requestable request = PackageBuilder.ReadDeserializedPackage();
        System.out.println(request.GetType());
        switch (request.GetType()) {
            case Token:
                Token token = (Token) request;
                return token.GetToken();
            case BAD_REQ:
                break;
        }
        return null;
    }
    public static void RequestMessageSender(Message message)
    {
        PackageBuilder.SendSerializedPackage(message);
    }
    public static void RequestNicknameSender(String login)
    {
        PackageBuilder.SendSerializedPackage(new Nickname(login));
    }
    public static void RequestAllMessagesSender()
    {
        PackageBuilder.SendSerializedPackage(new AllMessages());
    }
    public static ArrayList<Message> ResponseAllMessagesHandler()
    {
        Requestable request = PackageBuilder.ReadDeserializedPackage();
        System.out.println("REQUEST TYPE::   "+ request.GetType());
        switch (request.GetType())
        {
            case AllMessages:
                AllMessages messages = (AllMessages) request;
                System.out.println("Messages::  "+ messages.GetMessages());
                return messages.GetMessages();
        }
        return null;
    }
    public static String ResponseNicknameHandler()
    {
        Requestable request = PackageBuilder.ReadDeserializedPackage();
        switch (request.GetType())
        {
            case Nickname:
                Nickname nickname = (Nickname) request;
                return nickname.getNickname();
        }
        return null;
    }
    private static String getHashedPWD(String password)
    {
        return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }

}
